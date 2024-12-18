package com.mogun.presentation.viewmodel.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mogun.domain.model.BasketProduct
import com.mogun.domain.model.Product
import com.mogun.domain.usecase.BasketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketUseCase: BasketUseCase
) : ViewModel() {
    val basketProduct = basketUseCase.getBasketProducts()
    private val _eventFlow = MutableSharedFlow<BasketEvent>()
    val eventFlow: SharedFlow<BasketEvent> = _eventFlow

    fun dispatch(action: BasketAction) {
        when (action) {
            is BasketAction.RemoveProduct -> removeBasketProduct(action.product)
            is BasketAction.CheckoutBasket -> checkoutBasket(action.products)
        }
    }

    private fun removeBasketProduct(product: Product) {
        viewModelScope.launch {
            basketUseCase.removeBasketProduct(product)
        }
    }

    private fun checkoutBasket(products: List<BasketProduct>) {
        viewModelScope.launch {
            basketUseCase.checkoutBasket(products)
        }
    }
}

// 뷰모델에서 스크린으로
sealed class BasketEvent {
    object ShowSnackbar: BasketEvent()
}

// 스크린에서 뷰모델로
sealed class BasketAction {
    data class RemoveProduct(val product: Product): BasketAction()
    data class CheckoutBasket(val products: List<BasketProduct>): BasketAction()
}