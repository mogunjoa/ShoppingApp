package com.mogun.domain.repository

import com.mogun.domain.model.PurchaseHistory
import kotlinx.coroutines.flow.Flow

interface PurchaseHistoryRepository {
    fun getPurchaseHistory(): Flow<List<PurchaseHistory>>
}