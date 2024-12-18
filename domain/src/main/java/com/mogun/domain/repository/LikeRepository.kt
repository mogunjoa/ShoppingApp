package com.mogun.domain.repository

import com.mogun.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface LikeRepository {

    fun getLikeProduct(): Flow<List<Product>>
}