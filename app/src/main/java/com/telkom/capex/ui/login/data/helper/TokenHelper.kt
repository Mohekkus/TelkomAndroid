package com.telkom.capex.ui.login.data.helper

import com.telkom.capex.ui.login.data.model.AuthTokenResponse
import retrofit2.Response

interface TokenHelper {

    suspend fun getToken(): Response<AuthTokenResponse>
}