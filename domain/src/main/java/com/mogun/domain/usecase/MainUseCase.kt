package com.mogun.domain.usecase

import com.mogun.domain.model.BaseModel
import com.mogun.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainUseCase @Inject constructor(private val mainRepository: MainRepository) {

    fun getModelList(): Flow<List<BaseModel>> {
        return mainRepository.getModelList()
    }
}