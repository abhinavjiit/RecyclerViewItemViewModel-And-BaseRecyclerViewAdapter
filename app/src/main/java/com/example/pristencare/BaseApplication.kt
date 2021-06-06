package com.example.pristencare

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {


    fun getRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl("https://api.flickr.com")
            .addConverterFactory(GsonConverterFactory.create()).build()


}