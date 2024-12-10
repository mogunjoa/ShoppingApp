package com.mogun.data.repository

import com.mogun.data.datasource.TempDataSource
import com.mogun.domain.model.TempModel
import com.mogun.domain.repository.TempRepository
import javax.inject.Inject

class TempRepositoryImpl @Inject constructor(
    private val dataSource: TempDataSource
): TempRepository {
    override fun getTempModel(): TempModel {
        return dataSource.getTempModel()
    }
}