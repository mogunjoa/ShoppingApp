package com.mogun.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.gson.Gson
import com.mogun.domain.model.Category
import com.mogun.presentation.ui.basket.BasketScreen
import com.mogun.presentation.ui.category.CategoryScreen
import com.mogun.presentation.ui.main.LikeScreen
import com.mogun.presentation.ui.main.MainCategoryScreen
import com.mogun.presentation.ui.main.MainHomeScreen
import com.mogun.presentation.ui.main.MyPageScreen
import com.mogun.presentation.ui.product_detail.ProductDetailScreen
import com.mogun.presentation.ui.search.SearchScreen
import com.mogun.presentation.ui.theme.ShoppingAppTheme
import com.mogun.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(googleSignInClient: GoogleSignInClient) {
    val viewModel = hiltViewModel<MainViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (NavigationItem.MainNav.isMainRoute(currentRoute)) {
                MainHeader(viewModel = viewModel, navHostController = navController)
            }
        },
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (NavigationItem.MainNav.isMainRoute(currentRoute)) {
                MainBottomNavigationBar(navController, currentRoute)
            }
        }
    ) {
        Box(modifier = Modifier.padding(top = it.calculateTopPadding() + 10.dp)) {
            MainNaviationScreen(
                viewModel = viewModel,
                navHostController = navController,
                googleSignInClient = googleSignInClient
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHeader(viewModel: MainViewModel, navHostController: NavHostController) {
    TopAppBar(
        title = { Text(text = "Shopping App") },
        actions = {
            IconButton(onClick = {
                viewModel.openSearchForm(navHostController)
            }) {
                Icon(Icons.Filled.Search, "SearchIcon")
            }
            IconButton(onClick = {
                viewModel.openBasket(navHostController)
            }) {
                Icon(Icons.Filled.ShoppingCart, "ShoppingIcon")
            }
        }
    )
}

@Composable
fun MainBottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    val bottomNavigationItems = listOf(
        NavigationItem.MainNav.Home,
        NavigationItem.MainNav.Category,
        NavigationItem.MainNav.LIKE,
        NavigationItem.MainNav.MyPage,
    )

    NavigationBar {
        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.route
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
fun MainNaviationScreen(
    viewModel: MainViewModel,
    navHostController: NavHostController,
    googleSignInClient: GoogleSignInClient
) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationRouteName.MAIN_HOME,
    ) {
        composable(NavigationRouteName.MAIN_HOME) {
            MainHomeScreen(navHostController, viewModel)
        }
        composable(NavigationRouteName.MAIN_CATEGORY) {
            MainCategoryScreen(viewModel, navHostController)
        }
        composable(NavigationRouteName.MAIN_MY_PAGE) {
            MyPageScreen(viewModel = viewModel, googleSignInClient = googleSignInClient)
        }
        composable(NavigationRouteName.MAIN_LIKE) {
            LikeScreen(navHostController = navHostController, viewModel = viewModel)
        }
        composable(NavigationRouteName.BASKET) {
            BasketScreen()
        }
        composable(
            CategoryNav.routeWithArgName(),
            arguments = CategoryNav.arguments,
            deepLinks = CategoryNav.deepLink
        ) {
            val category = CategoryNav.findArgument(it)
            if (category != null) {
                CategoryScreen(navHostController = navHostController, category = category)
            }
        }
        composable(
            NavigationRouteName.PRODUCT_DETAIL + "/{product}",
            arguments = listOf(navArgument("product") { type = NavType.StringType })
        ) {
            val productString = it.arguments?.getString("product")
            if (productString != null) {
                ProductDetailScreen(productString)
            }
        }
        composable(NavigationRouteName.SEARCH) {
            SearchScreen(navHostController)
        }
    }
}