package com.telkom.capex.data.services

import com.telkom.capex.login.data.model.AuthTokenResponse
import dagger.Provides
import retrofit2.Response
import retrofit2.http.GET

interface LoginService {

    @GET("/login")
    suspend fun getToken(): Response<AuthTokenResponse>
}