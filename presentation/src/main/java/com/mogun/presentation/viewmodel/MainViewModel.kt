package com.mogun.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.mogun.domain.model.AccountInfo
import com.mogun.domain.model.Banner
import com.mogun.domain.model.BannerList
import com.mogun.domain.model.BaseModel
import com.mogun.domain.model.Carousel
import com.mogun.domain.model.Category
import com.mogun.domain.model.Product
import com.mogun.domain.model.Ranking
import com.mogun.domain.usecase.AccountUseCase
import com.mogun.domain.usecase.CategoryUseCase
import com.mogun.domain.usecase.LikeUseCase
import com.mogun.domain.usecase.MainUseCase
import com.mogun.presentation.deligate.BannerDelegate
import com.mogun.presentation.deligate.CategoryDelegate
import com.mogun.presentation.deligate.ProductDelegate
import com.mogun.presentation.model.BannerListVM
import com.mogun.presentation.model.BannerVM
import com.mogun.presentation.model.CarouselVM
import com.mogun.presentation.model.PresentationVM
import com.mogun.presentation.model.ProductVM
import com.mogun.presentation.model.RankingVM
import com.mogun.presentation.ui.NavigationItem
import com.mogun.presentation.ui.NavigationRouteName
import com.mogun.presentation.utils.NavigationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase,
    categoryUseCase: CategoryUseCase,
    private val accountUseCase: AccountUseCase,
    likeUseCase: LikeUseCase
) : ViewModel(), ProductDelegate, BannerDelegate, CategoryDelegate {
    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount: StateFlow<Int> = _columnCount
    val modelList = mainUseCase.getModelList().map(::convertToPresentationVM)
    val likeProducts = likeUseCase.getLikeProduct().map(::convertToPresentationVM)
    val categories = categoryUseCase.getCategories()
    val accountInfo = accountUseCase.getAccountInfo()

    fun openBasket(navHostController: NavHostController) {
        NavigationUtil.navigate(navHostController, NavigationRouteName.BASKET)
    }

    fun signIn(accountInfo: AccountInfo) {
        viewModelScope.launch {
            accountUseCase.signIn(accountInfo)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            accountUseCase.signOut()
        }
    }

    fun openSearchForm(navHostController: NavHostController) {
        NavigationUtil.navigate(navHostController, NavigationRouteName.SEARCH)
    }

    fun updateColumnCount(count: Int) {
        viewModelScope.launch {
            _columnCount.emit(count)
        }
    }

    override fun openProduct(navController: NavHostController, product: Product) {
        NavigationUtil.navigate(navController, NavigationRouteName.PRODUCT_DETAIL, product)
    }

    override fun likeProduct(product: Product) {
        viewModelScope.launch {
            mainUseCase.likeProduct(product)
        }
    }

    override fun openBanner(bannerId: String) {

    }

    override fun openCategory(navHostController: NavHostController, category: Category) {
        NavigationUtil.navigate(navHostController, NavigationRouteName.CATEGORY, category)
    }

    private fun convertToPresentationVM(list: List<BaseModel>): List<PresentationVM<out BaseModel>> {
        return list.map { model ->
            when (model) {
                is Product -> ProductVM(model, this)
                is Ranking -> RankingVM(model, this)
                is Carousel -> CarouselVM(model, this)
                is Banner -> BannerVM(model, this)
                is BannerList -> BannerListVM(model, this)
            }
        }
    }

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}