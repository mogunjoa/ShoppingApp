package com.mogun.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.mogun.domain.model.Banner
import com.mogun.domain.model.BannerList
import com.mogun.domain.model.Category
import com.mogun.domain.model.Product
import com.mogun.domain.usecase.CategoryUseCase
import com.mogun.domain.usecase.MainUseCase
import com.mogun.presentation.ui.NavigationRouteName
import com.mogun.presentation.utils.NavigationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(mainUseCase: MainUseCase, categoryUseCase: CategoryUseCase): ViewModel() {
    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount: StateFlow<Int> = _columnCount
    val modelList = mainUseCase.getModelList()
    val categories = categoryUseCase.getCategories()

    fun openSearchForm() {
        println("OPEN SEARCH FORM")
    }

    fun updateColumnCount(count: Int) {
        viewModelScope.launch {
            _columnCount.emit(count)
        }
    }

    fun openProduct(product: Product) {

    }

    fun openCarouselProduct(product: Product) {

    }

    fun openBanner(banner: Banner) {

    }

    fun openBannerList(banner: BannerList) {

    }

    fun openRankinglProduct(product: Product) {

    }

    fun openCategory(navController: NavHostController, category: Category) {
        NavigationUtil.navigate(navController, NavigationRouteName.CATEGORY, category)
    }

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}