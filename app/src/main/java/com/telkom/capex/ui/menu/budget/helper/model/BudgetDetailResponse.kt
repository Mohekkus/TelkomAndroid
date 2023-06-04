package com.telkom.capex.ui.menu.budget.helper.model


import com.fasterxml.jackson.annotation.JsonProperty

data class BudgetDetailResponse(@JsonProperty("Request")
                                val request: String = "",
                                @JsonProperty("Error")
                                val error: String? = null,
                                @JsonProperty("Code")
                                val code: Int = 0,
                                @JsonProperty("Result")
                                val result: List<BudgetDetailResultItem>?)


data class BudgetDetailResultItem(@JsonProperty("dtedc")
                                  val dtedc: String = "",
                                  @JsonProperty("strnamamitra")
                                  val strnamamitra: String = "",
                                  @JsonProperty("bintnilaikontrak")
                                  val bintnilaikontrak: Long? = 0,
                                  @JsonProperty("boolstatus")
                                  val boolstatus: Boolean = false,
                                  @JsonProperty("intsucceed")
                                  val intsucceed: Int = 0,
                                  @JsonProperty("txtdetailproyek")
                                  val txtdetailproyek: String = "",
                                  @JsonProperty("strnamakontrak")
                                  val strnamakontrak: String = "",
                                  @JsonProperty("strnamaorg")
                                  val strnamaorg: String = "",
                                  @JsonProperty("errdescription")
                                  val errdescription: String = "",
                                  @JsonProperty("dttoc")
                                  val dttoc: String = "")


