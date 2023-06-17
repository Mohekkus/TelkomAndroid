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


data class ResultItem(@JsonProperty("bintsumbast")
                      val bintsumbast: Long = 0,
                      @JsonProperty("intperbastdone")
                      val intperbastdone: Long = 0,
                      @JsonProperty("intsucceed")
                      val intsucceed: Int = 0,
                      @JsonProperty("bintsisapekerjaan")
                      val bintsisapekerjaan: Long = 0,
                      @JsonProperty("intpersisapekerjaan")
                      val intpersisapekerjaan: Long = 0,
                      @JsonProperty("intcountkontrakaktif")
                      val intcountkontrakaktif : Long = 0,
                      @JsonProperty("intcountcontrakselesai")
                      val intcountcontrakselesaiNBSP : Long = 0,
                      @JsonProperty("errdescription")
                      val errdescription: String = "",
                      @JsonProperty("bintbastyear")
                      val bintbastyear: Long = 0,
                      @JsonProperty("bintsumnikon")
                      val bintsumnikon: Long = 0,
                      @JsonProperty("intcountkont")
                      val intcountkont: Long = 0)


