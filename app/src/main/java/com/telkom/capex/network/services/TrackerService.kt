package com.telkom.capex.network.services

import com.telkom.capex.network.access.AppAccess
import com.telkom.capex.ui.menu.tracker.fragments.doc.model.DocResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TrackerService {

    @POST("/api")
    suspend fun getDOC(
        @Body access: AppAccess
    ) : Response<DocResponse>


    @POST("/api")
    suspend fun getDOCSearch(
        @Body access: AppAccess
    ) : Response<DocResponse>

    @POST("/api")
    fun getBOP()

}