package com.mogun.data.deserializer

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mogun.domain.model.Banner
import com.mogun.domain.model.BannerList
import com.mogun.domain.model.BaseModel
import com.mogun.domain.model.Carousel
import com.mogun.domain.model.ModelType
import com.mogun.domain.model.Product
import com.mogun.domain.model.Ranking
import java.lang.reflect.Type

class BaseModelDeserializer: JsonDeserializer<BaseModel> {
    companion object {
        private const val TYPE = "type"
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BaseModel? {
        val root = json?.asJsonObject
        val gson = GsonBuilder().create()

        val typeString = root?.get(TYPE)?.asString ?: ""

        return when(ModelType.valueOf(typeString)) {
            ModelType.PRODUCT -> gson.fromJson(root, Product::class.java)
            ModelType.BANNER -> gson.fromJson(root, Banner::class.java)
            ModelType.BANNER_LIST -> gson.fromJson(root, BannerList::class.java)
            ModelType.CAROUSEL -> gson.fromJson(root, Carousel::class.java)
            ModelType.RANKING -> gson.fromJson(root, Ranking::class.java)
        }
    }
}