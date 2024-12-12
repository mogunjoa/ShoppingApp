package com.mogun.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mogun.domain.model.Category
import com.mogun.domain.model.Price
import com.mogun.domain.model.Product
import com.mogun.domain.model.SalesStatus
import com.mogun.domain.model.Shop
import com.mogun.presentation.R
import com.mogun.presentation.deligate.ProductDelegate
import com.mogun.presentation.model.PresentationVM
import com.mogun.presentation.model.ProductVM
import com.mogun.presentation.ui.theme.Purple80
import com.mogun.presentation.ui.theme.ShoppingAppTheme

@Composable
fun ProductCard(presentationVM: ProductVM) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(10.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // 배경색을 흰색으로 설정
        ),
    ) {
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
                fontWeight = FontWeight.SemiBold,
                text = presentationVM.model.shop.shopName
            )
            Text(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                text = presentationVM.model.productName
            )
            Price(product = presentationVM.model)
        }
    }
}

@Composable
fun Price(product: Product) {
    when (product.price.salesStatus) {
        SalesStatus.ON_SALE -> {
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                text = "${product.price.originPrice}원"
            )
        }
        SalesStatus.ON_DISCOUNT -> {
            Text(
                fontSize = 14.sp,
                text = "${product.price.originPrice}원",
                style = TextStyle(textDecoration = TextDecoration.LineThrough)
            )
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Purple80,
                text = "${product.price.finalPrice}원"
            )
        }
        SalesStatus.SOLD_OUT -> {
            Text(
                fontSize = 18.sp,
                color = Color(0xFF666666),
                text = "판매종료"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProductCard() {
    ShoppingAppTheme {
        ProductCard(
            ProductVM(
                model = Product(
                    productId = "1",
                    productName = "상품 이름",
                    imageUrl = "",
                    price = Price(
                        30000,
                        30000,
                        SalesStatus.ON_SALE
                    ),
                    category = Category.Top,
                    shop = Shop(
                        "1",
                        "샵 이름",
                        "",
                    ),
                    isNew = false,
                    isFreeShipping = false,
                ),
                object : ProductDelegate {
                    override fun openProduct(product: Product) {

                    }
                }
            )
        )
    }
}

@Preview
@Composable
private fun PreviewProductCardDiscount() {
    ProductCard(
        ProductVM(
            model = Product(
                productId = "1",
                productName = "상품 이름",
                imageUrl = "",
                price = Price(
                    30000,
                    20000,
                    SalesStatus.ON_DISCOUNT
                ),
                category = Category.Top,
                shop = Shop(
                    "1",
                    "샵 이름",
                    "",
                ),
                isNew = false,
                isFreeShipping = false,
            ),
            object : ProductDelegate {
                override fun openProduct(product: Product) {

                }
            }
        )
    )
}

@Preview
@Composable
private fun PreviewProductCardSoldOut() {
        ProductCard(
            ProductVM(
                model = Product(
                    productId = "1",
                    productName = "상품 이름",
                    imageUrl = "",
                    price = Price(
                        30000,
                        20000,
                        SalesStatus.SOLD_OUT
                    ),
                    category = Category.Top,
                    shop = Shop(
                        "1",
                        "샵 이름",
                        "",
                    ),
                    isNew = false,
                    isFreeShipping = false,
                ),
                object : ProductDelegate {
                    override fun openProduct(product: Product) {

                    }
                }
            )
        )
}