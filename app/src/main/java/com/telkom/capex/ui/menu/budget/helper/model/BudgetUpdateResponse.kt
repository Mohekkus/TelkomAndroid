package com.telkom.capex.ui.menu.budget.helper.model


import com.fasterxml.jackson.annotation.JsonProperty

data class BudgetUpdateResultItem(@JsonProperty("error")
                                  val error: String = "")


data class BudgetUpdateResponse(@JsonProperty("Request")
                                val request: String = "",
                                @JsonProperty("Error")
                                val error: String? = null,
                                @JsonProperty("Code")
                                val code: Int = 0,
                                @JsonProperty("Result")
                                val result: List<BudgetUpdateResultItem>?)


