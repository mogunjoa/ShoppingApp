package com.mogun.presentation.deligate

import androidx.navigation.NavHostController
import com.mogun.domain.model.Product

interface ProductDelegate {
    fun openProduct(navHostController: NavHostController, product: Product)

    fun likeProduct(product: Product)
}