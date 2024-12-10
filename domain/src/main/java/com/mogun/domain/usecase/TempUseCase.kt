package com.mogun.domain.usecase

import com.mogun.domain.model.TempModel
import com.mogun.domain.repository.TempRepository
import javax.inject.Inject

class TempUseCase @Inject constructor(private val repository: TempRepository) {

    fun getTempModel(): TempModel {
        return repository.getTempModel()
    }
}