package com.mogun.data.repository

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mogun.domain.model.Product
import com.mogun.domain.repository.MainRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InputStreamReader
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): MainRepository {
    override fun getProductList(): Flow<List<Product>> = flow {
        val inputStream = context.assets.open("product_list.json")
        val inputStreamBuilder = InputStreamReader(inputStream)
        val jsonString = inputStreamBuilder.readText()
        val type = object : TypeToken<List<Map<String, Any>>>() {}.type

        val rawList: List<Map<String, Any>> = GsonBuilder().create().fromJson(jsonString, type)

        // "type"이 "PRODUCT"인 데이터만 필터링하고, Product로 변환
        val filteredList = rawList.filter { it["type"] == "PRODUCT" }
            .mapNotNull { item ->
                try {
                    GsonBuilder().create().fromJson(
                        GsonBuilder().create().toJson(item),
                        Product::class.java
                    )
                } catch (e: Exception) {
                    null
                }
            }

        emit(filteredList)
    }
}