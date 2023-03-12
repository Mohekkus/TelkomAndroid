package com.telkom.capex.ui.menu.dashboard.helper.model

import com.fasterxml.jackson.annotation.JsonProperty

data class DashboardResponse(@JsonProperty("Request")
                             val request: String = "",
                             @JsonProperty("Error")
                             val error: String? = null,
                             @JsonProperty("Code")
                             val code: Int = 0,
                             @JsonProperty("Result")
                             val result: List<ResultItem>?)