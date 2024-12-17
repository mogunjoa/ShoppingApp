package com.mogun.domain.repository

import com.mogun.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {
    fun getProductDetail(productId: String): Flow<Product>
}