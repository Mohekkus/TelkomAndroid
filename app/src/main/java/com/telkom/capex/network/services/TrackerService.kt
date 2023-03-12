package com.telkom.capex.network.services

import retrofit2.http.POST

interface TrackerService {

    @POST("/api")
    fun getDOC()

    @POST("/api")
    fun getBOP()

}