package com.roxx.bidmaster.domain.model

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(message: String, val redirectToLogin: Boolean = false) : Result<T>(message = message)
}