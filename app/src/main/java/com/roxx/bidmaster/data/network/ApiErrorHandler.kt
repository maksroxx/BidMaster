package com.roxx.bidmaster.data.network

import android.util.Log
import retrofit2.HttpException
import java.io.IOException
import com.roxx.bidmaster.domain.model.Result

object ApiErrorHandler {

    suspend fun <T> handleApiCall(apiCall: suspend () -> Result<T>): Result<T> {
        return try {
            apiCall()
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: IOException) {
            Log.e("API Call", "Network error: ${e.message}")
            Result.Error("Network error")
        } catch (e: Exception) {
            Log.e("API Call", "Unexpected error: ${e.message}")
            Result.Error("Unexpected error")
        }
    }

    private fun <T> handleHttpException(e: HttpException): Result<T> {
        return when (e.code()) {
            400 -> {
                Log.e("API Call", "Bad request: ${e.message()}")
                Result.Error("Bad request")
            }

            401 -> {
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unauthorized access"
                Log.e("API Call", "Unauthorized: $errorMessage")
                Result.Error(errorMessage, true)
            }

            403 -> {
                Log.e("API Call", "Forbidden: ${e.message()}")
                Result.Error("Access forbidden")
            }

            404 -> {
                Log.e("API Call", "Not found: ${e.message()}")
                Result.Error("Resource not found")
            }

            409 -> {
                Log.e("API Call", "Conflict: ${e.message()}")
                Result.Error("User already exists")
            }

            422 -> {
                Log.e("API Call", "Unprocessable Entity: ${e.message()}")
                Result.Error("Validation error")
            }

            500 -> {
                Log.e("API Call", "Internal Server Error: ${e.message()}")
                Result.Error("Internal server error")
            }

            503 -> {
                Log.e("API Call", "Service Unavailable: ${e.message()}")
                Result.Error("Service unavailable")
            }

            else -> {
                Log.e("API Call", "HTTP error: ${e.code()} - ${e.message()}")
                Result.Error("HTTP error")
            }
        }
    }
}