package com.mogun.domain.repository

import com.mogun.domain.model.Product

interface MainRepository {
    fun getProductList(): List<Product>
}