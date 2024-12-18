package com.mogun.presentation.ui.basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mogun.domain.model.BasketProduct
import com.mogun.domain.model.Product
import com.mogun.presentation.R
import com.mogun.presentation.ui.component.Price
import com.mogun.presentation.viewmodel.basket.BasketViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.mogun.presentation.ui.popupSnackBar
import com.mogun.presentation.ui.theme.Purple80
import com.mogun.presentation.utils.NumberUtils
import com.mogun.presentation.viewmodel.basket.BasketAction
import com.mogun.presentation.viewmodel.basket.BasketEvent

@Composable
fun BasketScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: BasketViewModel = hiltViewModel()
) {
    val basketProduct by viewModel.basketProduct.collectAsState(initial = listOf())
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                BasketEvent.ShowSnackbar -> {
                    popupSnackBar(
                        scope = scope,
                        scaffoldState = snackbarHostState,
                        message = "결제 되었습니다."
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(basketProduct.size) { index ->
                BasketProductCard(basketProduct = basketProduct[index]) { product ->
                    viewModel.dispatch(BasketAction.RemoveProduct(product))
                }
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.dispatch(BasketAction.CheckoutBasket(basketProduct))
            },
            colors = ButtonDefaults.buttonColors(containerColor = Purple80),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                Icons.Filled.Check, "CheckIcon"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                fontSize = 16.sp,
                text = "${getTotalPrice(basketProduct)} 결제하기"
            )
        }
    }
}

@Composable
fun BasketProductCard(basketProduct: BasketProduct, removeProduct: (Product) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_image),
                contentDescription = "description",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .weight(1f)
            ) {
                Text(
                    fontSize = 14.sp,
                    text = basketProduct.product.shop.shopName,
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                )
                Text(
                    fontSize = 14.sp,
                    text = basketProduct.product.productName,
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                )
                Price(product = basketProduct.product)
            }
            Text(
                text = "${basketProduct.count}개",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(30.dp)
            )
        }
        IconButton(
            onClick = { removeProduct(basketProduct.product) },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(Icons.Filled.Close, "CloseIcon")
        }
    }
}

private fun getTotalPrice(list: List<BasketProduct>): String {
    val totalPrice = list.sumOf { it.product.price.finalPrice * it.count }
    return NumberUtils.numberFormatPrice(totalPrice)
}