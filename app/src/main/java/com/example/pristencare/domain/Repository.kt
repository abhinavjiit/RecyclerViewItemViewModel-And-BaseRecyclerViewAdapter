package com.example.pristencare.domain

import com.example.pristencare.model.RequestModel
import com.example.pristencare.model.ResponseModel
import com.example.pristencare.utils.IResult
import kotlinx.coroutines.flow.Flow


interface Repository {

    suspend fun getImages(requestModel: RequestModel): Flow<IResult<ResponseModel>>
}