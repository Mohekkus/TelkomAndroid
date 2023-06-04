package com.telkom.capex.ui.menu.budget.helper.model


import com.fasterxml.jackson.annotation.JsonProperty

data class BudgetDetailSmileResultItem(@JsonProperty("intsucceed")
                                       val intsucceed: Int = 0,
                                       @JsonProperty("errdescription")
                                       val errdescription: String = "",
                                       @JsonProperty("totalsmile")
                                       val totalsmile: Long = 0,
                                       @JsonProperty("status")
                                       val status: List<Long>?)


data class BudgetDetailSmileResponse(@JsonProperty("Request")
                                     val request: String = "",
                                     @JsonProperty("Error")
                                     val error: String? = null,
                                     @JsonProperty("Code")
                                     val code: Int = 0,
                                     @JsonProperty("Result")
                                     val result: List<BudgetDetailSmileResultItem>?)


