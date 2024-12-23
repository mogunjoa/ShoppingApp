package com.mogun.domain.model

data class Carousel(
    val carouselId: String,
    val title: String,
    val productList: List<Product>,
    override val type: ModelType = ModelType.CAROUSEL
): BaseModel()
