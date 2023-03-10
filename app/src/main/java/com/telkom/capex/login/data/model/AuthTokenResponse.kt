package com.telkom.capex.login.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthTokenResponse(@JsonProperty("token")
                             val token: String = "")