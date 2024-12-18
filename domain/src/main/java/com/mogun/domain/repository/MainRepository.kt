package com.mogun.domain.repository

import com.mogun.domain.model.BaseModel
import com.mogun.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getModelList(): Flow<List<BaseModel>>

    suspend fun likeProduct(product: Product)
}