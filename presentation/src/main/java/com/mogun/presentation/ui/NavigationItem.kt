package com.mogun.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.mogun.domain.model.Category
import com.mogun.domain.model.Product
import com.mogun.presentation.ui.NavigationRouteName.CATEGORY
import com.mogun.presentation.ui.NavigationRouteName.MAIN_CATEGORY
import com.mogun.presentation.ui.NavigationRouteName.MAIN_HOME
import com.mogun.presentation.ui.NavigationRouteName.MAIN_MY_PAGE
import com.mogun.presentation.ui.NavigationRouteName.PRODUCT_DETAIL

sealed class NavigationItem(open val route: String) {
    sealed class MainNav(override val route: String, val icon: ImageVector, val name: String): NavigationItem(route) {
            object Home : MainNav(MAIN_HOME, Icons.Filled.Home, MAIN_HOME)
            object Category : MainNav(MAIN_CATEGORY, Icons.Filled.Star, MAIN_CATEGORY)
            object MyPage : MainNav(MAIN_MY_PAGE, Icons.Filled.AccountBox, MAIN_MY_PAGE)

        companion object {
            fun isMainRoute(route: String?): Boolean {
                return when(route) {
                    MAIN_HOME, MAIN_CATEGORY, MAIN_MY_PAGE -> true
                    else -> false
                }
            }
        }
    }

    data class CategoryNav( val category: Category): NavigationItem(CATEGORY)
    data class ProductNav(val product: Product): NavigationItem(PRODUCT_DETAIL)
}

object NavigationRouteName {
    const val MAIN_HOME = "main_home"
    const val MAIN_CATEGORY = "main_category"
    const val MAIN_MY_PAGE = "main_myPage"
    const val CATEGORY = "category"
    const val PRODUCT_DETAIL = "product_detail"
}