package com.mogun.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mogun.data.db.dao.BasketDao
import com.mogun.data.db.dao.LikeDao
import com.mogun.data.db.dao.PurchaseDao
import com.mogun.data.db.dao.PurchaseHistoryDao
import com.mogun.data.db.dao.SearchDao
import com.mogun.data.db.entity.BasketProductEntity
import com.mogun.data.db.entity.LikeProductEntity
import com.mogun.data.db.entity.PurchaseHistoryEntity
import com.mogun.data.db.entity.PurchaseProductEntity
import com.mogun.data.db.entity.SearchKeywordEntity

@Database(
    entities = [
        PurchaseProductEntity::class,
        LikeProductEntity::class,
        BasketProductEntity::class,
        SearchKeywordEntity::class,
        PurchaseHistoryEntity::class
    ],
    version = 4,
)
abstract class ApplicationDatabase: RoomDatabase() {
    companion object {
        const val DB_NAME = "applicationDatabase.db"
    }

    abstract fun purchaseDao(): PurchaseDao
    abstract fun likeDao(): LikeDao
    abstract fun basketDao(): BasketDao
    abstract fun searchDao(): SearchDao
    abstract fun purchaseHistoryDao(): PurchaseHistoryDao
}