package com.example.pristencare

import kotlinx.coroutines.flow.Flow


interface Repository {

    suspend fun getImages(requestModel: RequestModel): Flow<IResult<ResponseModel>>
}