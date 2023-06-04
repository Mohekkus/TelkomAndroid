package com.telkom.capex.ui.menu.budget.helper.model


import com.fasterxml.jackson.annotation.JsonProperty

data class BudgetListJsonItem(@JsonProperty("actualPM")
                              val actualPM: Long? = null,
                              @JsonProperty("planningRKAP")
                              val planningRKAP: Long? = null,
                              @JsonProperty("namaKontrak")
                              val namaKontrak: String = "",
                              @JsonProperty("kontrakMitra")
                              val kontrakMitra: String = "",
                              @JsonProperty("statusKontrak")
                              val statusKontrak: String = "",
                              @JsonProperty("planningPM")
                              val planningPM: Long? = null,
                              @JsonProperty("idKontrak")
                              val idKontrak: Int? = null)


data class BudgetListResultItem(@JsonProperty("totalplaningrkap")
                                val totalplaningrkap: Long = 0,
                                @JsonProperty("perplaningrkap")
                                val perplaningrkap: Double = 0.0,
                                @JsonProperty("intsucceed")
                                val intsucceed: Int = 0,
                                @JsonProperty("perplaningpm")
                                val perplaningpm: Double = 0.0,
                                @JsonProperty("totalactual")
                                val totalactual: Long = 0,
                                @JsonProperty("errdescription")
                                val errdescription: String = "",
                                @JsonProperty("json_array")
                                val jsonArray: List<BudgetListJsonItem>?,
                                @JsonProperty("totalplaningpm")
                                val totalplaningpm: Long = 0)


data class BudgetListResponse(@JsonProperty("Request")
                              val request: String = "",
                              @JsonProperty("Error")
                              val error: String? = null,
                              @JsonProperty("Code")
                              val code: Int = 0,
                              @JsonProperty("Result")
                              val result: List<BudgetListResultItem>?)


