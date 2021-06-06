package com.example.pristencare

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import retrofit2.Response
import java.io.IOException

typealias NetworkAPIInvoke<T> = suspend () -> Response<T>

typealias MapResponse<T> = suspend () -> Response<T>


@ExperimentalCoroutinesApi
suspend fun <T : Any> performNetworkCall(
    messageInCaseOfError: String = "Network error",
    allowRetries: Boolean = true,
    numberOfRetries: Int = 2,
    networkApiCall: NetworkAPIInvoke<T>
): Flow<IResult<T>> {
    var delayDuration = 1000L
    val delayFactor = 2
    return flow {
        val response = networkApiCall()
        if (response.isSuccessful) {
            //save in db via polling
            // steps
            response.body()?.let { emit(IResult.Success(it)) }
                ?: emit(IResult.Error(IOException("API call successful but empty response body")))
            return@flow
        }
        emit(IResult.Error(IOException("API call failed with error - ${response.errorBody()?.string() ?: messageInCaseOfError}")))
        return@flow
    }.catch { e ->
        emit(IResult.Error(IOException("Exception during network API call: ${e.message}")))
        return@catch
    }.retryWhen { cause, attempt ->
        if (!allowRetries || attempt > numberOfRetries || cause !is IOException) return@retryWhen false
        delay(delayDuration)
        delayDuration *= delayFactor
        return@retryWhen true
    }.flowOn(Dispatchers.IO)
}