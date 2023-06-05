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