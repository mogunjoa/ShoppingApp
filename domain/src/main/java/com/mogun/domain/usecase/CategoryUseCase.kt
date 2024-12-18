package com.mogun.domain.usecase

import com.mogun.domain.model.Category
import com.mogun.domain.model.Product
import com.mogun.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryUseCase @Inject constructor(private val repository: CategoryRepository) {
    fun getCategories(): Flow<List<Category>> = repository.getCategories()
    fun getProductByCategory(category: Category) : Flow<List<Product>> {
        return repository.getProductByCategory(category)
    }

    suspend fun likeProduct(product: Product) {
        repository.likeProduct(product)
    }
}