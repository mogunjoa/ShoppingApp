package com.mogun.domain.usecase

import com.mogun.domain.model.Product
import com.mogun.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikeUseCase @Inject constructor(
    private val repository: LikeRepository
) {

    fun getLikeProduct(): Flow<List<Product>> {
        return repository.getLikeProduct()
    }
}