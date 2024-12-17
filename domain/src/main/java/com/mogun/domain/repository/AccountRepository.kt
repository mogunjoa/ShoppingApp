package com.mogun.domain.repository

import com.mogun.domain.model.AccountInfo
import kotlinx.coroutines.flow.StateFlow

interface AccountRepository {
    fun getAccountInfo(): StateFlow<AccountInfo?>

    suspend fun signInGoogle(accountInfo: AccountInfo)

    suspend fun signOutGoogle()
}