package com.mogun.data.repository

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mogun.data.datasource.ProductDataSource
import com.mogun.data.deserializer.BaseModelDeserializer
import com.mogun.domain.model.BaseModel
import com.mogun.domain.model.Product
import com.mogun.domain.repository.MainRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InputStreamReader
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource
): MainRepository {
    override fun getModelList(): Flow<List<BaseModel>> {
        return dataSource.getProducts()
    }
}