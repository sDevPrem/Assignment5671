package com.sdevprem.roadcastassignment.data.datasource.interceptor

import com.sdevprem.roadcastassignment.data.datasource.constants.ApiConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val urlWithAuth = request.url().newBuilder()
            .addQueryParameter(ApiConfig.AUTH_QUERY_KEY, ApiConfig.API_KEY)
            .build()

        val newRequest = request.newBuilder().url(urlWithAuth).build()

        return chain.proceed(newRequest)
    }
}