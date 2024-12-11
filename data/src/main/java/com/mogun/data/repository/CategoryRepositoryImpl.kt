package com.mogun.data.repository

import com.mogun.domain.model.Category
import com.mogun.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override fun getCategories(): Flow<List<Category>> = flow {
        emit(
            listOf(
                Category.Top,
                Category.Bag,
                Category.Outerwear,
                Category.Dress,
                Category.FashionAccessories,
                Category.Pants,
                Category.Skirt,
                Category.Shoes,
            )
        )
    }
}