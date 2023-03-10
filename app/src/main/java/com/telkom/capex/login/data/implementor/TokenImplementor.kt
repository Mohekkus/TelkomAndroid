package com.telkom.capex.login.data.implementor

import com.telkom.capex.network.services.LoginService
import com.telkom.capex.login.data.helper.TokenHelper
import com.telkom.capex.login.data.model.AuthTokenResponse
import retrofit2.Response
import javax.inject.Inject

class TokenImplementor @Inject constructor(private val loginService: LoginService) : TokenHelper {
    override suspend fun getToken(): Response<AuthTokenResponse> = loginService.getToken()
}