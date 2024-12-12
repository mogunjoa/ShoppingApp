package com.mogun.presentation.ui.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mogun.domain.model.Banner
import com.mogun.domain.model.BannerList
import com.mogun.domain.model.Carousel
import com.mogun.domain.model.ModelType
import com.mogun.domain.model.Product
import com.mogun.domain.model.Ranking
import com.mogun.presentation.model.BannerListVM
import com.mogun.presentation.model.BannerVM
import com.mogun.presentation.model.CarouselVM
import com.mogun.presentation.model.ProductVM
import com.mogun.presentation.model.RankingVM
import com.mogun.presentation.ui.component.BannerCard
import com.mogun.presentation.ui.component.BannerListCard
import com.mogun.presentation.ui.component.CarouselCard
import com.mogun.presentation.ui.component.ProductCard
import com.mogun.presentation.ui.component.RankingCard
import com.mogun.presentation.viewmodel.MainViewModel

@Composable
fun MainHomeScreen(viewModel: MainViewModel) {
    val modelList by viewModel.modelList.collectAsState(initial = listOf())
    val columnCount by viewModel.columnCount.collectAsState()

    LazyVerticalGrid(columns = GridCells.Fixed(columnCount)) {
        items(modelList.size, span = { index ->
            val item = modelList[index]
            val spanCount = getSpanCountByType(item.model.type, columnCount)

            GridItemSpan(spanCount)
        }) {

            val item = modelList[it]
            when (item) {
                is ProductVM -> ProductCard(presentationVM = item)
                is BannerVM -> BannerCard(presentationVM = item)
                is BannerListVM -> BannerListCard(presentationVM = item)
                is CarouselVM -> CarouselCard(presentationVM = item)
                is RankingVM -> RankingCard(presentationVM = item)
            }
        }
    }
}

private fun getSpanCountByType(type: ModelType, defaultColumnCount: Int): Int {
    return when (type) {
        ModelType.PRODUCT -> 1
        ModelType.BANNER, ModelType.BANNER_LIST, ModelType.CAROUSEL, ModelType.RANKING -> defaultColumnCount
    }
}

