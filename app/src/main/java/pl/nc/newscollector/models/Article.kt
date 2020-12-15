package pl.nc.newscollector.models

import com.google.gson.annotations.SerializedName


data class Article (
        @field:SerializedName("news_title") val title: String,
        @field:SerializedName("publish_date") val date: String,
        @field:SerializedName("news_description") val description: String,
        @field:SerializedName("news_link") val link: String,
        @field:SerializedName("keyword_value") val keywords: String, // TODO Investigate if we provide user with keywords or single keyword
        @field:SerializedName("website_name") val websiteName: String,
        @field:SerializedName("feed_name") val feedName: String,
        var isExpanded: Boolean = false

)