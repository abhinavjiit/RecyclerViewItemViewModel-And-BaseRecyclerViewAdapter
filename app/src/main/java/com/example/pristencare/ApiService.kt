package com.example.pristencare

import retrofit2.Response
import retrofit2.http.GET
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

}