package com.mogun.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mogun.domain.model.Product
import com.mogun.domain.model.Ranking
import com.mogun.presentation.R
import java.nio.file.WatchEvent

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RankingCard(model: Ranking, onClick: (Product) -> Unit) {
    val pagerState = rememberPagerState()
    val pageCount = model.productList.size / DEFAULT_RANKING_ITEM_COUNT

    Column {
        Text(
            text = model.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
        )
        HorizontalPager(
            count = pageCount,
            state = pagerState,
            contentPadding = PaddingValues(end = 50.dp)
        ) { index ->
            Column {
                RankingProductCard(
                    index = index * 3,
                    product = model.productList[index * 3],
                    onClick = onClick
                )
                RankingProductCard(
                    index = index * 3 + 1,
                    product = model.productList[index * 3 + 1],
                    onClick = onClick
                )
                RankingProductCard(
                    index = index * 3 + 2,
                    product = model.productList[index * 3 + 2],
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun RankingProductCard(index: Int, product: Product, onClick: (Product) -> Unit) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text("${index + 1}", fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp))
        Image(
            painter = painterResource(id = R.drawable.product_image),
            contentDescription = "description",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(0.7f)
        )
        Column(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
            Text(
                fontSize = 14.sp,
                text = product.shop.shopName,
                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
            Text(
                fontSize = 14.sp,
                text = product.productName,
                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
            Price(product = product)
        }
    }
}

private const val DEFAULT_RANKING_ITEM_COUNT = 3