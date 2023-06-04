package com.telkom.capex.ui.menu.dashboard.helper.model


import com.fasterxml.jackson.annotation.JsonProperty

data class DashboardPieResultItem(@JsonProperty("intsucceed")
                      val intsucceed: Int = 0,
                      @JsonProperty("errdescription")
                      val errdescription: String = "",
                      @JsonProperty("totalsmile")
                      val totalsmile: Int = 0,
                      @JsonProperty("status")
                      val status: List<Long>?)


data class DashboardPieChartResponse(@JsonProperty("Request")
                                     val request: String = "",
                                     @JsonProperty("Error")
                                     val error: String? = null,
                                     @JsonProperty("Code")
                                     val code: Int = 0,
                                     @JsonProperty("Result")
                                     val result: List<DashboardPieResultItem>?)


