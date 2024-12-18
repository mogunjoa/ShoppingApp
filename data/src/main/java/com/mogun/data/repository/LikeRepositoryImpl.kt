package com.mogun.data.repository

import com.mogun.data.db.dao.LikeDao
import com.mogun.data.db.entity.toDomainModel
import com.mogun.domain.model.Product
import com.mogun.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val likeDao: LikeDao
): LikeRepository {
    override fun getLikeProduct(): Flow<List<Product>> {
        return likeDao.getAll().map {entities ->
            entities.map { it.toDomainModel() }
        }
    }
}