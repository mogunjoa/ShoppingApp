package com.mogun.domain.repository

import com.mogun.domain.model.Product
import com.mogun.domain.model.SearchFilter
import com.mogun.domain.model.SearchKeyword
import kotlinx.coroutines.flow.Flow

interface SearchRepository  {
    suspend fun search(searchKeyword: SearchKeyword, filters: List<SearchFilter>): Flow<List<Product>>

    fun getSearchKeywords(): Flow<List<SearchKeyword>>
}