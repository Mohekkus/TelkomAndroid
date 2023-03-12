package com.telkom.capex.ui.login.data.repo

import com.telkom.capex.network.services.LoginService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginService: LoginService){

    suspend fun getToken() = loginService.getToken()
}