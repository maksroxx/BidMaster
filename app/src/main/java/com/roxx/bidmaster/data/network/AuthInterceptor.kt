package com.roxx.bidmaster.data.network

import com.roxx.bidmaster.domain.preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preferences: Preferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (originalRequest.header("Requires-Auth") != null) {
            val token = preferences.getToken()
            if (token != null) {
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                return chain.proceed(newRequest)
            }
        }
        return chain.proceed(originalRequest)
    }
}