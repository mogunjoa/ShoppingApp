package com.mogun.presentation.model

import androidx.navigation.NavHostController
import com.mogun.domain.model.Carousel
import com.mogun.domain.model.Product
import com.mogun.presentation.deligate.ProductDelegate

class CarouselVM(model: Carousel, private val productDelegate: ProductDelegate): PresentationVM<Carousel>(model) {

    fun openCarouselProduct(navHostController: NavHostController, product: Product) {
        productDelegate.openProduct(navHostController, product)
        sendCarouselLog()
    }

    private fun sendCarouselLog() {

    }

    fun likeProduct(product: Product) {
        productDelegate.likeProduct(product)
    }
}