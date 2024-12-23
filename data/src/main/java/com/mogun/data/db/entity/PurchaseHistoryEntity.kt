package com.mogun.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mogun.data.db.converter.PurchaseHistoryConverter
import com.mogun.domain.model.BasketProduct
import com.mogun.domain.model.PurchaseHistory
import java.time.ZonedDateTime

@Entity(tableName = "history")
@TypeConverters(PurchaseHistoryConverter::class)
data class PurchaseHistoryEntity(
    val basketList: List<BasketProduct>,
    val purchaseAt: ZonedDateTime
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun PurchaseHistoryEntity.toDomain(): PurchaseHistory {
    return PurchaseHistory(
        basketList = basketList,
        purchaseAt = purchaseAt
    )
}

fun PurchaseHistory.toEntity(): PurchaseHistoryEntity {
    return PurchaseHistoryEntity(
        basketList = basketList,
        purchaseAt = purchaseAt
    )
}