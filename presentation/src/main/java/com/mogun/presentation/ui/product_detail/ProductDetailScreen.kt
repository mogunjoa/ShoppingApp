package com.mogun.presentation.ui.product_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mogun.presentation.R
import com.mogun.presentation.ui.theme.Purple80
import com.mogun.presentation.utils.NumberUtils
import com.mogun.presentation.viewmodel.product_detail.ProductDetailViewModel

@Composable
fun ProductDetailScreen(productId: String, viewModel: ProductDetailViewModel = hiltViewModel()) {
    val product by viewModel.product.collectAsState()

    LaunchedEffect(key1 = productId) {
        viewModel.updateProduct(productId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Card(
                        modifier = Modifier.size(50.dp),
                        shape = CircleShape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.product_image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "${product?.shop?.shopName}",
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "${product?.productName}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "${product?.productName}의 상품 상세 페이지 설명글입니다.",
                    fontSize = 12.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${NumberUtils.numberFormatPrice(product?.price?.finalPrice)}원",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = { viewModel.addBasket(product) },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Purple80,
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "ShoppingCartIcon"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        fontSize = 16.sp,
                        text = "장바구니 담기"
                    )
                }
            }
        }
    }
}