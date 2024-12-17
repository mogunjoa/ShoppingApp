package com.mogun.domain.usecase

import com.mogun.domain.model.AccountInfo
import com.mogun.domain.repository.AccountRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    fun getAccountInfo(): StateFlow<AccountInfo?> {
        return accountRepository.getAccountInfo()
    }

    suspend fun signInGoogle(accountInfo: AccountInfo) {
        accountRepository.signInGoogle(accountInfo)
    }

    suspend fun signOutGoogle() {
        accountRepository.signOutGoogle()
    }
}