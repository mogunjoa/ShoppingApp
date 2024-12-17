package com.mogun.data.repository

import com.mogun.data.datasource.PreferenceMangerDatasource
import com.mogun.domain.model.AccountInfo
import com.mogun.domain.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val preferenceMangerDatasource: PreferenceMangerDatasource
): AccountRepository {
    private val accountInfoFlow = MutableStateFlow(preferenceMangerDatasource.getAccountInfo())

    override fun getAccountInfo(): StateFlow<AccountInfo?> {
        return accountInfoFlow
    }

    override suspend fun signInGoogle(accountInfo: AccountInfo) {
        preferenceMangerDatasource.putAccountInfo(accountInfo)
        accountInfoFlow.emit(accountInfo)
    }

    override suspend fun signOutGoogle() {
        preferenceMangerDatasource.removeAccountInfo()
        accountInfoFlow.emit(null)
    }
}