package com.mogun.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.mogun.presentation.ui.basket.BasketScreen
import com.mogun.presentation.ui.category.CategoryScreen
import com.mogun.presentation.ui.main.LikeScreen
import com.mogun.presentation.ui.main.MainCategoryScreen
import com.mogun.presentation.ui.main.MainHomeScreen
import com.mogun.presentation.ui.main.MyPageScreen
import com.mogun.presentation.ui.product_detail.ProductDetailScreen
import com.mogun.presentation.ui.purchase_history.PurchaseHistoryScreen
import com.mogun.presentation.ui.search.SearchScreen
import com.mogun.presentation.utils.NavigationUtil
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
            MainHeader(
                viewModel = viewModel,
                navHostController = navController,
                currentRoute = currentRoute
            )
        },
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (MainNav.isMainRoute(currentRoute)) {
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
fun MainHeader(
    viewModel: MainViewModel,
    navHostController: NavHostController,
    currentRoute: String?
) {
    TopAppBar(
        title = { Text(text = NavigationUtil.findDestination(currentRoute).title) },
        navigationIcon = {
            if (!MainNav.isMainRoute(currentRoute)) {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            } else {
                null
            }
        },
        actions = {
            if (MainNav.isMainRoute(currentRoute)) {
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
        }
    )
}

@Composable
fun MainBottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    val bottomNavigationItems = listOf(
        MainNav.Home,
        MainNav.Category,
        MainNav.Like,
        MainNav.MyPage,
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
                    NavigationUtil.navigate(
                        navController, item.route,
                        navController.graph.startDestinationRoute
                    )
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
        startDestination = MainNav.Home.route,
    ) {
        composable(route = MainNav.Home.route, deepLinks = MainNav.Home.deepLink) {
            MainHomeScreen(navHostController, viewModel)
        }
        composable(route = MainNav.Category.route, deepLinks = MainNav.Category.deepLink) {
            MainCategoryScreen(viewModel, navHostController)
        }
        composable(route = MainNav.MyPage.route, deepLinks = MainNav.MyPage.deepLink) {
            MyPageScreen(viewModel = viewModel, googleSignInClient = googleSignInClient, navHostController = navHostController)
        }
        composable(route = MainNav.Like.route, deepLinks = MainNav.Like.deepLink) {
            LikeScreen(navHostController = navHostController, viewModel = viewModel)
        }
        composable(route = BasketNav.route, deepLinks = BasketNav.deepLink) {
            BasketScreen()
        }
        composable(route = PurchaseHistoryNav.route, deepLinks = PurchaseHistoryNav.deepLink) {
            PurchaseHistoryScreen()
        }
        composable(
            route = CategoryNav.routeWithArgName(),
            arguments = CategoryNav.arguments,
            deepLinks = CategoryNav.deepLink
        ) {
            val category = CategoryNav.findArgument(it)
            if (category != null) {
                CategoryScreen(navHostController = navHostController, category = category)
            }
        }
        composable(
            route = ProductDetailNav.routeWithArgName(),
            arguments = ProductDetailNav.arguments,
            deepLinks = ProductDetailNav.deepLink
        ) {
            val productString = ProductDetailNav.findArgument(it)
            if (productString != null) {
                ProductDetailScreen(productString)
            }
        }
        composable(route = SearchNav.route, deepLinks = SearchNav.deepLink) {
            SearchScreen(navHostController)
        }
    }
}