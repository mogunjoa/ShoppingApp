package com.mogun.presentation.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mogun.presentation.ui.main.MainInsideScreen
import com.mogun.presentation.ui.theme.ShoppingAppTheme
import com.mogun.presentation.viewmodel.MainViewModel

sealed class MainNavigationItem(val route: String, val icon: ImageVector, val name: String) {
    object Main : MainNavigationItem("Main", Icons.Filled.Home, "Main")
    object Category : MainNavigationItem("Category", Icons.Filled.Star, "Category")
    object MyPage : MainNavigationItem("MyPage", Icons.Filled.AccountBox, "MyPage")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    Scaffold(
        topBar = { Header(viewModel) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            MainBottomNavigationBar(navController)
        }
    ) {
        Spacer(modifier = Modifier.padding(it))
        MainNaviationScreen(
            viewModel = viewModel, navController = navController,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(viewModel: MainViewModel) {
    TopAppBar(
        title = { Text(text = "Shopping App") },
        actions = {
            IconButton(onClick = {
                viewModel.openSearchForm()
            }) {
                Icon(Icons.Filled.Search, "SearchIcon")
            }
        }
    )
}

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
    val bottomNavigationItems = listOf(
        MainNavigationItem.Main,
        MainNavigationItem.Category,
        MainNavigationItem.MyPage,
    )

    NavigationBar {
        // 백스택 관리
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

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
fun MainNaviationScreen(viewModel: MainViewModel, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainNavigationItem.Main.route,
    ) {
        composable(MainNavigationItem.Main.route) {
            Spacer(modifier = Modifier.padding())
            MainInsideScreen(viewModel)
        }
        composable(MainNavigationItem.Category.route) {
            Text(text = "Hello Category")
        }
        composable(MainNavigationItem.MyPage.route) {
            Text(text = "Hello MyPage")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingAppTheme {
        MainScreen()
    }
}