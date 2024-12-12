package com.mogun.presentation.model

import com.mogun.domain.model.BaseModel

sealed class PresentationVM<T: BaseModel>(val model: T) {

}