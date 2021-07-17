package com.example.pristencare.apiservice

import com.example.pristencare.model.PhotoDetail
import com.example.pristencare.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    //https://live.staticflickr.com/{server-id}/{id}_{o-secret}_o.{o-format}

    @GET("/services/rest/")
    suspend fun getImages(
        @Query("method") method: String,
        @Query("api_key") key: String,
        @Query("format") format: String,
        @Query("nojsoncallback") callback: String,
        @Query("safe_search") search: String,
        @Query("tags") tag: String,
        @Query("per_page") perPage: String,
        @Query("page") page: Int
    ): Response<ResponseModel>

    @GET("/photos/{photo_number}")
    suspend fun getPhotoDetail(@Path("photo_number") number:Int):Response<PhotoDetail>

}