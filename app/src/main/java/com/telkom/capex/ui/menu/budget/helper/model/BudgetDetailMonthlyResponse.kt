package com.telkom.capex.ui.menu.budget.helper.model


import com.fasterxml.jackson.annotation.JsonProperty

data class BudgetDetailMonthlyResultItem(@JsonProperty("intsucceed")
                                         val intsucceed: Int = 0,
                                         @JsonProperty("mdata")
                                         val mdata: List<MonthlyDataItem>?,
                                         @JsonProperty("errdescription")
                                         val errdescription: String = "")


data class BudgetDetailMonthlyResponse(@JsonProperty("Request")
                                       val request: String = "",
                                       @JsonProperty("Error")
                                       val error: String? = null,
                                       @JsonProperty("Code")
                                       val code: Int = 0,
                                       @JsonProperty("Result")
                                       val result: List<BudgetDetailMonthlyResultItem>?)


data class MonthlyDataItem(@JsonProperty("planningRKAP")
                           var planningRKAP: Long = 0,
                           @JsonProperty("actual")
                           var actual: Long = 0,
                           @JsonProperty("planningPM")
                           var planningPM: Long = 0)


