package com.mogun.domain.repository

import com.mogun.domain.model.BasketProduct
import com.mogun.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    fun getBasketProducts(): Flow<List<BasketProduct>>

    suspend fun removeBasketProduct(product: Product)
}