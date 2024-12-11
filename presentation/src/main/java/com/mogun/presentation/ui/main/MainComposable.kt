package com.mogun.presentation.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mogun.presentation.ui.common.ProductCard
import com.mogun.presentation.viewmodel.MainViewModel

@Composable
fun MainInsideScreen(viewModel: MainViewModel) {
    val productList by viewModel.productList.collectAsState(initial = listOf())
    val columnCount by viewModel.columnCount.collectAsState()

    LazyVerticalGrid (columns = GridCells.Fixed(columnCount), modifier = Modifier.padding(top = 60.dp)) {
        items(productList.size) {
            ProductCard(
                product = productList[it],
            ) {

            }
        }
    }
}