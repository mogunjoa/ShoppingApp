package com.mogun.data.repository

import com.mogun.data.datasource.ProductDataSource
import com.mogun.data.db.dao.LikeDao
import com.mogun.data.db.entity.toLikeProductEntity
import com.mogun.domain.model.Category
import com.mogun.domain.model.Product
import com.mogun.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource,
    private val likeDao: LikeDao
) : CategoryRepository {
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

    override fun getProductByCategory(category: Category): Flow<List<Product>> {
        return dataSource.getHomeComponents().map { list ->
            list.filterIsInstance<Product>().filter { product ->
                product.category.categoryId == category.categoryId
            }
        }.combine(likeDao.getAll()) { productList, likeList ->
            productList.map { updateLikeStatus(it, likeList.map { it.productId }) }
        }
    }

    override suspend fun likeProduct(product: Product) {
        if(product.isLike) {
            likeDao.delete(product.productId)
        } else {
            likeDao.insert(product.toLikeProductEntity())
        }
    }

    private fun updateLikeStatus(product: Product, likeProductIds: List<String>): Product {
        return product.copy(isLike = likeProductIds.contains(product.productId))
    }
}