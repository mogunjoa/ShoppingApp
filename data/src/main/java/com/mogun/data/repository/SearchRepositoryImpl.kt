package com.mogun.data.repository

import com.mogun.data.datasource.ProductDataSource
import com.mogun.data.db.dao.LikeDao
import com.mogun.data.db.dao.SearchDao
import com.mogun.data.db.entity.SearchKeywordEntity
import com.mogun.data.db.entity.toDomain
import com.mogun.data.db.entity.toLikeProductEntity
import com.mogun.domain.model.Product
import com.mogun.domain.model.SearchFilter
import com.mogun.domain.model.SearchKeyword
import com.mogun.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource,
    private val searchDao: SearchDao,
    private val likeDao: LikeDao
) : SearchRepository {
    override suspend fun search(searchKeyword: SearchKeyword): Flow<List<Product>> {
        searchDao.insert(SearchKeywordEntity(searchKeyword.keyword))
        return dataSource.getProducts().combine(likeDao.getAll()) { productList, likeList ->
            productList.map { updateLikeStatus(it, likeList.map { it.productId }) }
        }
    }

    override fun getSearchKeywords(): Flow<List<SearchKeyword>> {
        return searchDao.getAll().map { it.map { entity -> entity.toDomain() } }
    }

    override suspend fun likeProduct(product: Product) {
        if(product.isLike) {
            likeDao.delete(product.productId)
        } else {
            likeDao.insert(product.toLikeProductEntity().copy(isLike = true))
        }
    }

    private fun updateLikeStatus(product: Product, likeProductIds: List<String>): Product {
        return product.copy(isLike = likeProductIds.contains(product.productId))
    }
}