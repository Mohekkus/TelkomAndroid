package com.telkom.capex.ui.menu.dashboard.helper.model


import com.fasterxml.jackson.annotation.JsonProperty

data class DashboardYearResultItem(@JsonProperty("intsucceed")
                                   val intsucceed: Int = 0,
                                   @JsonProperty("arrays")
                                   val arrays: List<Long>?,
                                   @JsonProperty("errdescription")
                                   val errdescription: String = "")


data class DashboardYearResponse(@JsonProperty("Request")
                                 val request: String = "",
                                 @JsonProperty("Error")
                                 val error: String? = null,
                                 @JsonProperty("Code")
                                 val code: Int = 0,
                                 @JsonProperty("Result")
                                 val result: List<DashboardYearResultItem>?)


