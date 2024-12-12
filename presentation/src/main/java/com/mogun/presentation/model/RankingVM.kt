package com.mogun.presentation.model

import com.mogun.domain.model.Product
import com.mogun.domain.model.Ranking
import com.mogun.presentation.deligate.ProductDelegate

class RankingVM(model: Ranking, private val productDelegate: ProductDelegate) :
    PresentationVM<Ranking>(model), ProductDelegate by productDelegate {

    fun openRankingProduct(product: Product) {
        productDelegate.openProduct(product)
        sendRankingLog()
    }

    private fun sendRankingLog() {

    }
}