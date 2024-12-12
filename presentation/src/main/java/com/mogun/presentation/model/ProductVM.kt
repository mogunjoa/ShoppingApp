package com.mogun.presentation.model

import com.mogun.domain.model.Product
import com.mogun.presentation.deligate.ProductDelegate

class ProductVM(model: Product, private val productDelegate: ProductDelegate) :
    PresentationVM<Product>(model), ProductDelegate by productDelegate {

}