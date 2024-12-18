package com.mogun.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mogun.domain.model.Product
import com.mogun.presentation.R
import com.mogun.presentation.model.CarouselVM

@Composable
fun CarouselCard(navHostController: NavHostController, presentationVM: CarouselVM) {
    val scrollState = rememberLazyListState()
    Column {
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            text = presentationVM.model.title,
            modifier = Modifier.padding(10.dp)
        )
        LazyRow(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(presentationVM.model.productList.size) { index ->
                CarouselProductCard(product = presentationVM.model.productList[index], presentationVM) { model ->
                    presentationVM.openCarouselProduct(navHostController, model)
                }
            }
        }
    }
}

@Composable
private fun CarouselProductCard(product: Product, presentationVM: CarouselVM, onClick: (Product) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(150.dp)
            .wrapContentHeight()
            .padding(10.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // 배경색을 흰색으로 설정
        ),
        onClick = { onClick(product) }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { presentationVM.likeProduct(product) },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    if (product.isLike) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    "FavoriteIcon"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.product_image),
                    contentDescription = "description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
                Text(
                    fontSize = 14.sp,
                    text = product.productName
                )
                Price(product = product)
            }
        }
    }
}