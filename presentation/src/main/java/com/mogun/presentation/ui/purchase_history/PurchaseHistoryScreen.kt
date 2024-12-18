package com.mogun.presentation.ui.purchase_history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mogun.domain.model.BasketProduct
import com.mogun.domain.model.PurchaseHistory
import com.mogun.presentation.viewmodel.purchase_history.PurchaseHistoryViewModel
import com.mogun.presentation.R
import com.mogun.presentation.ui.component.Price
import com.mogun.presentation.utils.NumberUtils
import androidx.compose.runtime.getValue

@Composable
fun PurchaseHistoryScreen(
    viewModel: PurchaseHistoryViewModel = hiltViewModel()
) {
    val productHistory by viewModel.purchaseHistory.collectAsState(initial = listOf())

    LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        productHistory.forEach {
            PurchaseHistoryCard(it)
        }
    }
}

fun LazyListScope.PurchaseHistoryCard(purchaseHistory: PurchaseHistory) {
        item {
            Text(
                fontSize = 16.sp,
                text = "결제 시기: ${purchaseHistory.purchaseAt}"
            )
        }
        items(purchaseHistory.basketList.size) { index ->
            val currentItem = purchaseHistory.basketList[index]

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.product_image),
                    contentDescription = "purchaseImage",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                        .weight(1f)
                ) {
                    Text(
                        fontSize = 14.sp,
                        text = "${currentItem.product.shop.shopName} - ${currentItem.product.productName}"
                    )
                    Price(product = currentItem.product)
                }
                Text(
                    text = "${currentItem.count} 개",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(30.dp, 0.dp, 0.dp, 0.dp)
                )
            }
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 20.dp),
                fontSize = 16.sp,
                text = "${getTotalPrice(purchaseHistory.basketList)} 결제완료"
            )
        }
}

private fun getTotalPrice(list: List<BasketProduct>): String {
    val totalPrice = list.sumOf { it.product.price.finalPrice * it.count }
    return NumberUtils.numberFormatPrice(totalPrice)
}