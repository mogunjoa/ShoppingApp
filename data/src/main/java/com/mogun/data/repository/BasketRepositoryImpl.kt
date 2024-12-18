package com.mogun.data.repository

import com.mogun.data.db.dao.BasketDao
import com.mogun.data.db.dao.PurchaseHistoryDao
import com.mogun.data.db.entity.PurchaseHistoryEntity
import com.mogun.data.db.entity.toDomainModel
import com.mogun.domain.model.BasketProduct
import com.mogun.domain.model.Product
import com.mogun.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val basketDao: BasketDao,
    private val purchaseHistoryDao: PurchaseHistoryDao
): BasketRepository {
    override fun getBasketProducts(): Flow<List<BasketProduct>> {
        return basketDao.getAll().map { list ->
            list.map { BasketProduct(it.toDomainModel(), it.count) }
        }
    }

    override suspend fun removeBasketProduct(product: Product) {
        return basketDao.delete(product.productId)
    }

    override suspend fun checkoutBasket(products: List<BasketProduct>) {
        purchaseHistoryDao.insert(PurchaseHistoryEntity(products, ZonedDateTime.now()))
        basketDao.deleteAll()
    }

}