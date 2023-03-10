package com.telkom.capex.login.data.repo

import com.telkom.capex.data.services.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginService: LoginService){

    suspend fun getToken() = loginService.getToken()
}