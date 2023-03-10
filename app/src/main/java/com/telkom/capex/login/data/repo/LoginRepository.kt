package com.telkom.capex.login.data.repo

import com.telkom.capex.data.services.LoginService
import com.telkom.capex.login.data.helper.TokenHelper
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginService: LoginService){

    suspend fun getToken() = loginService.getToken()
}