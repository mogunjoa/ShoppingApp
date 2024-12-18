package com.mogun.data.repository

import com.mogun.data.datasource.ProductDataSource
import com.mogun.data.db.dao.LikeDao
import com.mogun.data.db.entity.toLikeProductEntity
import com.mogun.domain.model.Banner
import com.mogun.domain.model.BannerList
import com.mogun.domain.model.BaseModel
import com.mogun.domain.model.Carousel
import com.mogun.domain.model.Product
import com.mogun.domain.model.Ranking
import com.mogun.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource,
    private val likeDao: LikeDao
): MainRepository {
    override fun getModelList(): Flow<List<BaseModel>> {
        return dataSource.getHomeComponents().combine(likeDao.getAll()) { homeComponents, likeList ->
            homeComponents.map { model -> mappingLike(model, likeList.map { it.productId }) }
        }
    }

    override suspend fun likeProduct(product: Product) {
        if(product.isLike) {
            likeDao.delete(product.productId)
        } else {
            likeDao.insert(product.toLikeProductEntity().copy(isLike = true))
        }
    }

    private fun mappingLike(baseModel: BaseModel, likeProductIds: List<String>): BaseModel {
        return when(baseModel) {
            is Carousel -> { baseModel.copy(productList = baseModel.productList.map { updateLikeStatus(it, likeProductIds) })}
            is Ranking -> { baseModel.copy(productList = baseModel.productList.map { updateLikeStatus(it, likeProductIds) })}
            is Product -> {  updateLikeStatus(baseModel, likeProductIds) }
            else -> baseModel
        }
    }

    private fun updateLikeStatus(product: Product, likeProductIds: List<String>): Product {
        return product.copy(isLike = likeProductIds.contains(product.productId))
    }
}