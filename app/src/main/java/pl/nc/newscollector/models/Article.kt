package pl.nc.newscollector.models

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


data class Article(
        @field:SerializedName("news_title")
        val title: String,
        @field:SerializedName("publish_date")
        val date: String,
        @field:SerializedName("news_description")
        val description: String,
        @field:SerializedName("news_link")
        val link: String,
        @field:SerializedName("keywords_string")
        val keywords: String, // TODO Investigate if we provide user with keywords or single keyword
        @field:SerializedName("website_name")
        val websiteName: String,
        @field:SerializedName("feed_name")
        val feedName: String,
        var isExpanded: Boolean = false
) {
    class Deserializer : ResponseDeserializable<ArrayList<Article>> {
        override fun deserialize(content: String): ArrayList<Article>? {
            val typeToken = object : TypeToken<ArrayList<Article>>() {}.type
            return Gson().fromJson(content, typeToken)
        }
    }
}