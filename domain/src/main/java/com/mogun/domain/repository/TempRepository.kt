package com.mogun.domain.repository

import com.mogun.domain.model.TempModel

interface TempRepository {
    fun getTempModel(): TempModel
}