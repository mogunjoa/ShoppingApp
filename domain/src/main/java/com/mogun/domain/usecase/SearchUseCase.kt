package com.mogun.domain.usecase

import com.mogun.domain.model.Product
import com.mogun.domain.model.SearchKeyword
import com.mogun.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend fun search(keyword: SearchKeyword): Flow<List<Product>> {
        return searchRepository.search(keyword)
    }

    fun getSearchKeywords(): Flow<List<SearchKeyword>> {
        return searchRepository.getSearchKeywords()
    }
}