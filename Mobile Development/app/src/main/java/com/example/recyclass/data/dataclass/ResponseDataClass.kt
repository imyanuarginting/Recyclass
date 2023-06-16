package com.example.recyclass.data.dataclass

import com.google.gson.annotations.SerializedName

data class ImageUploadResponse (
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class PlasticTypeResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("result")
    val result: String
)
data class ArticleResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("result")
    val result: List<Article>
)

data class Article(
    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("img_url")
    val img_url: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("publication_date")
    val publication_date: String?,

    @field:SerializedName("plastic_type")
    val plastic_type: String,
)