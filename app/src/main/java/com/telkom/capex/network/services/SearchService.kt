package com.telkom.capex.network.services

import com.telkom.capex.network.access.AppAccess
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardResponse
import com.telkom.capex.ui.menu.search.model.SearchContractModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchService {

    @POST("/api")
    suspend fun search(
        @Body access: AppAccess
    ) : Response<SearchContractModel>
}