package com.mogun.presentation.viewmodel.category

import androidx.lifecycle.ViewModel
import com.mogun.domain.model.Category
import com.mogun.domain.model.Product
import com.mogun.domain.usecase.CategoryUseCase
import com.mogun.presentation.deligate.ProductDelegate
import com.mogun.presentation.model.ProductVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val useCase: CategoryUseCase
): ViewModel(), ProductDelegate {
    private val _products = MutableStateFlow<List<ProductVM>>(listOf())
    val products: StateFlow<List<ProductVM>> = _products

    suspend fun updateCategory(category: Category) {
        useCase.getProductByCategory(category).collectLatest {
            _products.emit(convertToPresentationVM(it))
        }
    }

    override fun openProduct(product: Product) {

    }

    private fun convertToPresentationVM(list: List<Product>): List<ProductVM> {
        return list.map {
            ProductVM(it, this)
        }
    }
}