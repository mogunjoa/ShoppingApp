package com.mogun.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mogun.domain.model.SearchKeyword

@Entity(tableName = "search")
data class SearchKeywordEntity(
    @PrimaryKey
    val keyword: String
)

fun SearchKeywordEntity.toDomain(): SearchKeyword {
    return SearchKeyword(keyword = keyword)
}
