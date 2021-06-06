package com.example.pristencare.utils

sealed class IResult<out T : Any> {

    data class Success<out T : Any>(val data: T? = null) : IResult<T>()

    data class Error(val throwable: Throwable) : IResult<Nothing>()

    data class ErrorWithObject<out T : Any>(val throwable: Throwable, val data: T? = null) : IResult<Nothing>()

    object Loading : IResult<Nothing>()

}