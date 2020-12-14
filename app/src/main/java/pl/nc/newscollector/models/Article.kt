package pl.nc.newscollector.models

data class Article (
    val title: String,
    val date: String,
    val description: String,
    val link: String,
    val keywords: String, // TODO Investigate if we provide user with keywords or single keyword
    val isExpanded: Boolean = false
)