package com.telkom.capex.data.interceptor

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RetrofitInterceptor @Inject constructor(private val sharedPreferences: SharedPreferences): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //rewrite the request to add bearer token
        val newRequest = chain.request().newBuilder()
            .header("Authorization", "Bearer ${sharedPreferences.getString("token", "")}")
            .build()

        return chain.proceed(newRequest)
    }
}