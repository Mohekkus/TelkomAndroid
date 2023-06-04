package com.telkom.capex.network.services

import com.telkom.capex.network.access.AppAccess
import com.telkom.capex.ui.menu.budget.helper.model.BudgetDetailMonthlyResponse
import com.telkom.capex.ui.menu.budget.helper.model.BudgetDetailResponse
import com.telkom.capex.ui.menu.budget.helper.model.BudgetDetailSmileResponse
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResponse
import com.telkom.capex.network.access.BudgetUpdateAccess
import com.telkom.capex.ui.menu.budget.helper.model.BudgetUpdateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BudgetService {

    @POST("/api")
    suspend fun getBudget(
        @Body access: AppAccess
    ) : Response<BudgetListResponse>


    @POST("/api")
    suspend fun getDetailContract(
        @Body access: AppAccess
    ) : Response<BudgetDetailResponse>

    @POST("/api")
    suspend fun getDetailSmile(
        @Body access: AppAccess
    ) : Response<BudgetDetailSmileResponse>


    @POST("/api")
    suspend fun getDetailMonthly(
        @Body access: AppAccess
    ) : Response<BudgetDetailMonthlyResponse>

    @POST("/api")
    suspend fun updateDetail(
        @Body access: BudgetUpdateAccess
    ) : Response<BudgetUpdateResponse>
}