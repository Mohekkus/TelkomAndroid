package com.telkom.capex.ui.menu.budget.helper.model


import com.fasterxml.jackson.annotation.JsonProperty

data class BudgetListResultItem(@JsonProperty("intidkontrak")
                                val intidkontrak: Int = 0,
                                @JsonProperty("strnamamitra")
                                val strnamamitra: String = "",
                                @JsonProperty("bintplaningbast")
                                val bintplaningbast: Long = 0,
                                @JsonProperty("bintactualbast")
                                val bintactualbast: List<Long>?,
                                @JsonProperty("strnamakontrak")
                                val strnamakontrak: String = "",
                                @JsonProperty("inttahun")
                                val inttahun: Int = 0)


data class BudgetListResponse(@JsonProperty("Request")
                              val request: String = "",
                              @JsonProperty("Error")
                              val error: String = "",
                              @JsonProperty("Code")
                              val code: Int = 0,
                              @JsonProperty("Result")
                              val result: List<BudgetListResultItem>?)


