package com.mogun.data.datasource

import com.mogun.data.model.TestModelResponse

class TestDataSource {
    fun getTestModelResponse(): TestModelResponse {
        return TestModelResponse("response")
    }
}