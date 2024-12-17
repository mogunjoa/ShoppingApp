package com.mogun.domain.repository

import com.mogun.domain.model.Product
import com.mogun.domain.model.SearchKeyword
import kotlinx.coroutines.flow.Flow

interface SearchRepository  {
    suspend fun search(keyword: SearchKeyword): Flow<List<Product>>

    fun getSearchKeywords(): Flow<List<SearchKeyword>>
}