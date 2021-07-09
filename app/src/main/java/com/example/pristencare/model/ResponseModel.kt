package com.example.pristencare.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("photos")
    val photos: Photos
)


data class Photos(
    @SerializedName("photo")
    val photo: List<Photo>,
    @SerializedName("page")
    val page: Int = 1
)

@Keep
data class Photo(
    @SerializedName("farm")
    val farm: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("isfamily")
    val isfamily: Int,
    @SerializedName("isfriend")
    val isfriend: Int,
    @SerializedName("ispublic")
    val ispublic: Int,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("title")
    val title: String
)

data class RequestModel(var page: Int = 1)
