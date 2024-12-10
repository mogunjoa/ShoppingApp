package com.mogun.data.model

import com.mogun.domain.model.TestModel

class TestModelResponse(val name: String?)

fun TestModelResponse.toDomainModel(): TestModel? {
    if(name != null) {
        return TestModel(name)
    }
    return null
}