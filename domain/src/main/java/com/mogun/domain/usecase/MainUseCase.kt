package com.mogun.domain.usecase

import com.mogun.domain.model.Product
import com.mogun.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainUseCase @Inject constructor(private val mainRepository: MainRepository) {

    fun getProductList(): Flow<List<Product>> {
        return mainRepository.getProductList()
    }
}