package com.example.pristencare.domain

import com.example.pristencare.model.RequestModel
import com.example.pristencare.model.ResponseModel
import com.example.pristencare.apiservice.ApiService
import com.example.pristencare.utils.IResult
import com.example.pristencare.utils.performNetworkCall
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class RepositoryImpl (private val apiService: ApiService) :
    Repository {
    override suspend fun getImages(requestModel: RequestModel): Flow<IResult<ResponseModel>> {
        return performNetworkCall {
            apiService.getImages(
                method = "flickr.photos.search",
                key = "3e7cc266ae2b0e0d78e279ce8e361736",
                format = "json",
                callback = "1",
                search = "1",
                tag = "kitten",
                perPage = "10",
                page = requestModel.page
            )
        }
    }
}