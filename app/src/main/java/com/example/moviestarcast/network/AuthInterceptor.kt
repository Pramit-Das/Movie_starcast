package com.example.moviestarcast.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNTQxNjY4MDVkMjc3MGMyY2E5MmUzYWU1OGFiZmIzMCIsIm5iZiI6MTczNTAyNzY0MC4wOTIsInN1YiI6IjY3NmE2YmI4ZmRhYTdjMjU0OWE5ZTM0MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.o3Fs9-g3xVmZCVTkhLvBhBfZHGGfzgxjzXo3BygMCok")
            .build()
        return chain.proceed(request)
    }
}