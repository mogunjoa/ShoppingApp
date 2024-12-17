package com.mogun.presentation.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.mogun.domain.model.Product
import com.mogun.domain.model.SearchKeyword
import com.mogun.domain.usecase.SearchUseCase
import com.mogun.presentation.deligate.ProductDelegate
import com.mogun.presentation.model.ProductVM
import com.mogun.presentation.ui.NavigationRouteName
import com.mogun.presentation.utils.NavigationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchUseCase
) : ViewModel(), ProductDelegate {
    private val _searchResult = MutableStateFlow<List<ProductVM>>(listOf())
    val searchResult: StateFlow<List<ProductVM>> = _searchResult
    val searchKeyword = useCase.getSearchKeywords()

    fun search(keyword: String) {
        viewModelScope.launch {
            useCase.search(SearchKeyword(keyword = keyword)).collectLatest {
                _searchResult.emit(it.map(::convertToProductVM))
            }
        }
    }

    private fun convertToProductVM(product: Product): ProductVM {
        return ProductVM(product, this)
    }

    override fun openProduct(
        navHostController: NavHostController,
        product: Product
    ) {
        NavigationUtil.navigate(navHostController, NavigationRouteName.PRODUCT_DETAIL, product)
    }
}