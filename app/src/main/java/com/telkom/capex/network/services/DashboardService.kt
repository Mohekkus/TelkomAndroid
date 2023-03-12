package com.telkom.capex.network.services

import com.telkom.capex.network.access.AppAccess
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardResponse
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardYearResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DashboardService {

    @POST("/api")
    suspend fun getDashboard(
        @Body access: AppAccess
    ) : Response<DashboardResponse>

    @POST("/api")
    suspend fun getBarYear(
        @Body access: AppAccess
    ) : Response<DashboardYearResponse>
}