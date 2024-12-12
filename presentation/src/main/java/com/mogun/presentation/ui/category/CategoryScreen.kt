package com.mogun.presentation.ui.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mogun.domain.model.Category
import com.mogun.presentation.viewmodel.category.CategoryViewModel
import androidx.compose.runtime.getValue
import com.mogun.presentation.ui.component.ProductCard

@Composable
fun CategoryScreen(
    category: Category,
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val products by viewModel.products.collectAsState()

    LaunchedEffect(key1 = category) {
        viewModel.updateCategory(category)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(products.size) { index ->
            ProductCard(presentationVM = products[index])
        }
    }
}