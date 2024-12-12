package com.mogun.presentation.model

import com.mogun.domain.model.Carousel
import com.mogun.domain.model.Product
import com.mogun.presentation.deligate.ProductDelegate

class CarouselVM(model: Carousel, private val productDelegate: ProductDelegate): PresentationVM<Carousel>(model) {

    fun openCarouselProduct(product: Product) {
        productDelegate.openProduct(product)
        sendCarouselLog()
    }

    private fun sendCarouselLog() {

    }
}