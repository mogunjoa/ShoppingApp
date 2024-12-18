package com.mogun.domain.usecase

import com.mogun.domain.model.BasketProduct
import com.mogun.domain.model.Product
import com.mogun.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {

    fun getBasketProducts(): Flow<List<BasketProduct>> = basketRepository.getBasketProducts()

    suspend fun removeBasketProduct(product: Product) = basketRepository.removeBasketProduct(product = product)
}