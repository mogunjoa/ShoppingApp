package com.mogun.data.repository

import com.mogun.data.datasource.ProductDataSource
import com.mogun.domain.model.BaseModel
import com.mogun.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource
): MainRepository {
    override fun getModelList(): Flow<List<BaseModel>> {
        return dataSource.getHomeComponents()
    }
}