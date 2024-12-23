package com.mogun.domain.model

data class Ranking(
    val rankingId: String,
    val title: String,
    val productList: List<Product>,
    override val type: ModelType = ModelType.RANKING,
): BaseModel()