package com.telkom.capex.ui.menu.dashboard.helper.model


import com.fasterxml.jackson.annotation.JsonProperty

data class DashboardDivisionResponse(@JsonProperty("Request")
                                     val request: String = "",
                                     @JsonProperty("Error")
                                     val error: String? = null,
                                     @JsonProperty("Code")
                                     val code: Int = 0,
                                     @JsonProperty("Result")
                                     val result: List<DivisionResultItem>?)


data class DivisionResultItem(@JsonProperty("intsucceed")
                      val intsucceed: Int = 0,
                      @JsonProperty("intidorg")
                      val intidorg: Int = 0,
                      @JsonProperty("strnamaorg")
                      val strnamaorg: String = "",
                      @JsonProperty("errdescription")
                      val errdescription: String = "")


