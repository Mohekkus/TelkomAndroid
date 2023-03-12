package com.telkom.capex.network.services

import com.telkom.capex.ui.login.data.model.AuthTokenResponse
import retrofit2.Response
import retrofit2.http.GET

interface LoginService {

    @GET("/login")
    suspend fun getToken(): Response<AuthTokenResponse>
}