package com.telkom.capex.ui.menu.dashboard.helper.repo

import com.telkom.capex.network.access.AccessComposer
import com.telkom.capex.network.services.DashboardService
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardDivisionResponse
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardPieChartResponse
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardResponse
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardYearResponse
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class DashboardRepo @Inject constructor(
    private val dashboardService: DashboardService
    ) {

    suspend fun getDashboard(): Response<DashboardResponse> = dashboardService.getDashboard(
        AccessComposer.getAccess(
            "api_summary_dashboard"
        )
    )

    suspend fun getBarYear(year: Int): Response<DashboardYearResponse> = dashboardService.getBarYear(
        AccessComposer.getBarYear(
            "api_summary_yearbast_array",
            year
        )
    )

    suspend fun getDivision(): Response<DashboardDivisionResponse> = dashboardService.getDivisionList(
        AccessComposer.getAccess(
            "api_selectall_organisasi"
        )
    )

    suspend fun getPie(id: Int, preview: Boolean): Response<DashboardPieChartResponse> = dashboardService.getPie(
        AccessComposer.getPie(
            "api_summary_pie_preview",
            id,
            preview
        )
    )
}