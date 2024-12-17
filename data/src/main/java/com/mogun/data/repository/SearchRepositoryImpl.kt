package com.mogun.data.repository

import com.mogun.data.datasource.ProductDataSource
import com.mogun.data.db.dao.SearchDao
import com.mogun.data.db.entity.SearchKeywordEntity
import com.mogun.data.db.entity.toDomain
import com.mogun.domain.model.Product
import com.mogun.domain.model.SearchKeyword
import com.mogun.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource,
    private val searchDao: SearchDao,
) : SearchRepository {
    override suspend fun search(searchKeyword: SearchKeyword): Flow<List<Product>> {
        searchDao.insert(SearchKeywordEntity(searchKeyword.keyword))
        return dataSource.getProducts().map { list ->
            list.filter { it.productName.contains(searchKeyword.keyword) }
        }
    }

    override fun getSearchKeywords(): Flow<List<SearchKeyword>> {
        return searchDao.getAll().map { it.map { entity -> entity.toDomain() } }
    }
}