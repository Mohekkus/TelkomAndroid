package com.telkom.capex.menu.dashboard.helper.model

import com.fasterxml.jackson.annotation.JsonProperty


data class MonthlyBast(@JsonProperty("data")
                       val data: List<DataItem>?)


data class DataItem(@JsonProperty("duit")
                    val duit: String = "",
                    @JsonProperty("month")
                    val month: String = "",
                    @JsonProperty("page")
                    val page: String? = null
)


