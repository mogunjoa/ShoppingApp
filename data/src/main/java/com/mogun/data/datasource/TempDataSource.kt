package com.mogun.data.datasource

import com.mogun.domain.model.TempModel
import javax.inject.Inject

class TempDataSource @Inject constructor() {
    fun getTempModel(): TempModel {
        return TempModel("testModel")
    }
}