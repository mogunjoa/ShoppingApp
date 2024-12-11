package com.mogun.domain.repository

import com.mogun.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getProductList(): Flow<List<Product>>
}