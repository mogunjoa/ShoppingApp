package com.mogun.domain.usecase

import com.mogun.domain.model.PurchaseHistory
import com.mogun.domain.repository.PurchaseHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PurchaseHistoryUseCase @Inject constructor(
    private val repository: PurchaseHistoryRepository
) {

    fun getPurchaseHistory(): Flow<List<PurchaseHistory>> {
        return repository.getPurchaseHistory()
    }
}