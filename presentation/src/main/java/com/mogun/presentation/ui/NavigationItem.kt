package com.mogun.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mogun.domain.model.Category
import com.mogun.domain.model.Product
import com.mogun.presentation.ui.NavigationRouteName.BASKET
import com.mogun.presentation.ui.NavigationRouteName.CATEGORY
import com.mogun.presentation.ui.NavigationRouteName.DEEP_LINK_SCHEME
import com.mogun.presentation.ui.NavigationRouteName.MAIN_CATEGORY
import com.mogun.presentation.ui.NavigationRouteName.MAIN_HOME
import com.mogun.presentation.ui.NavigationRouteName.MAIN_LIKE
import com.mogun.presentation.ui.NavigationRouteName.MAIN_MY_PAGE
import com.mogun.presentation.ui.NavigationRouteName.PRODUCT_DETAIL
import com.mogun.presentation.ui.NavigationRouteName.SEARCH
import com.mogun.presentation.utils.GsonUtils

sealed class NavigationItem(open val route: String) {
    sealed class MainNav(override val route: String, val icon: ImageVector, val name: String) :
        NavigationItem(route) {
        object Home : MainNav(MAIN_HOME, Icons.Filled.Home, MAIN_HOME)
        object Category : MainNav(MAIN_CATEGORY, Icons.Filled.Star, MAIN_CATEGORY)
        object MyPage : MainNav(MAIN_MY_PAGE, Icons.Filled.AccountBox, MAIN_MY_PAGE)
        object LIKE : MainNav(MAIN_LIKE, Icons.Filled.Favorite, MAIN_LIKE)

        companion object {
            fun isMainRoute(route: String?): Boolean {
                return when (route) {
                    MAIN_HOME, MAIN_LIKE, MAIN_CATEGORY, MAIN_MY_PAGE -> true
                    else -> false
                }
            }
        }
    }

    data class CategoryNav(val category: Category) : NavigationItem(CATEGORY)
    data class ProductNav(val product: Product) : NavigationItem(PRODUCT_DETAIL)
    object SearchNav : NavigationItem(SEARCH)
    object BasketNav : NavigationItem(BASKET)
}

object CategoryNav: DestinationArg<Category> {
    override val route: String = NavigationRouteName.CATEGORY
    override val title: String = NavigationTitle.CATEGORY
    override val deepLink: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route/{$argName}" }
    )
    override val argName: String = "category"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) { type = NavType.StringType }
    )
    override fun navigateWithArg(arg: Category) = "$route/${arg}"
    override fun findArgument(navBackStackEntry: NavBackStackEntry): Category? {
        val categoryString = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<Category>(categoryString)
    }
}

interface Destination {
    val route: String
    val title: String
    val deepLink: List<NavDeepLink>
}

interface DestinationArg<T>: Destination {
    val argName: String
    val arguments: List<NamedNavArgument>

    fun routeWithArgName() = "$route/{$argName}"
    fun navigateWithArg(arg: T) = "$route/$arg"
    fun findArgument(navBackStackEntry: NavBackStackEntry): T?
}

object NavigationRouteName {
    const val DEEP_LINK_SCHEME = "mogunjoa://"
    const val MAIN_HOME = "main_home"
    const val MAIN_CATEGORY = "main_category"
    const val MAIN_MY_PAGE = "main_myPage"
    const val MAIN_LIKE = "main_like"
    const val CATEGORY = "category"
    const val PRODUCT_DETAIL = "product_detail"
    const val SEARCH = "search"
    const val BASKET = "basket"
}

object NavigationTitle {
    const val MAIN_HOME = "홈"
    const val MAIN_CATEGORY = "카테고리"
    const val MAIN_MY_PAGE = "마이페이지"
    const val MAIN_LIKE = "좋아요 모아보기"
    const val CATEGORY = "카테고리별 보기"
    const val PRODUCT_DETAIL = "상품 상세페이지"
    const val SEARCH = "검색"
    const val BASKET = "장바구니"
}