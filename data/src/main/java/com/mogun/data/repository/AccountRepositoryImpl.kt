package com.mogun.data.repository

import com.mogun.data.datasource.PreferenceMangerDatasource
import com.mogun.data.db.dao.BasketDao
import com.mogun.data.db.dao.LikeDao
import com.mogun.domain.model.AccountInfo
import com.mogun.domain.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val preferenceMangerDatasource: PreferenceMangerDatasource,
    private val basketDao: BasketDao,
    private val likeDao: LikeDao
): AccountRepository {
    private val accountInfoFlow = MutableStateFlow(preferenceMangerDatasource.getAccountInfo())

    override fun getAccountInfo(): StateFlow<AccountInfo?> {
        return accountInfoFlow
    }

    override suspend fun signIn(accountInfo: AccountInfo) {
        preferenceMangerDatasource.putAccountInfo(accountInfo)
        accountInfoFlow.emit(accountInfo)
    }

    override suspend fun signOut() {
        preferenceMangerDatasource.removeAccountInfo()
        accountInfoFlow.emit(null)
        basketDao.deleteAll()
        likeDao.deleteAll()
    }
}