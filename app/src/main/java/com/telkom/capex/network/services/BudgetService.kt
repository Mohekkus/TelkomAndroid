package com.telkom.capex.network.services

import retrofit2.http.POST

interface BudgetService {

    @POST("/api")
    fun getBudget()
}