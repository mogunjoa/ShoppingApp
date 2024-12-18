package com.mogun.domain.repository

import com.mogun.domain.model.Category
import com.mogun.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<List<Category>>
    fun getProductByCategory(category: Category): Flow<List<Product>>

    suspend fun likeProduct(product: Product)
}