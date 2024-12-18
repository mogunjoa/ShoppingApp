package com.mogun.data.repository

import com.mogun.data.db.dao.PurchaseHistoryDao
import com.mogun.data.db.entity.toDomain
import com.mogun.domain.model.PurchaseHistory
import com.mogun.domain.repository.PurchaseHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PurchaseHistoryRepositoryImpl @Inject constructor(
    private val purchaseHistoryDao: PurchaseHistoryDao
): PurchaseHistoryRepository {

    override fun getPurchaseHistory(): Flow<List<PurchaseHistory>> {
        return purchaseHistoryDao.getAll().map { list ->
            list.map { it.toDomain() }
        }
    }
}