package com.telkom.capex.login.data.helper

import com.telkom.capex.login.data.model.AuthTokenResponse
import retrofit2.Response

interface TokenHelper {

    suspend fun getToken(): Response<AuthTokenResponse>
}