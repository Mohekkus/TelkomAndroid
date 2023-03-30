package com.telkom.capex.network.services

import com.telkom.capex.network.access.AppAccess
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BudgetService {

    @POST("/api")
    suspend fun getBudget(
        @Body access: AppAccess
    ) : Response<BudgetListResponse>
}