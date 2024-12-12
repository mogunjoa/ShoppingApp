package com.mogun.presentation.model

import androidx.navigation.NavHostController
import com.mogun.domain.model.Product
import com.mogun.domain.model.Ranking
import com.mogun.presentation.deligate.ProductDelegate

class RankingVM(model: Ranking, private val productDelegate: ProductDelegate) :
    PresentationVM<Ranking>(model), ProductDelegate by productDelegate {

    fun openRankingProduct(navHostController: NavHostController, product: Product) {
        productDelegate.openProduct(navHostController, product)
        sendRankingLog()
    }

    private fun sendRankingLog() {

    }
}