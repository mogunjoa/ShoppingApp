package com.mogun.presentation.deligate

import com.mogun.domain.model.Product

interface ProductDelegate {
    fun openProduct(product: Product)
}