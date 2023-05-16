package com.telkom.capex.ui.menu.search.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ResultItem(@JsonProperty("intidkontrak")
                      val intidkontrak: Int = 0,
                      @JsonProperty("txtinfo")
                      val txtinfo: String = "",
                      @JsonProperty("dtedc")
                      val dtedc: String = "",
                      @JsonProperty("strnamamitra")
                      val strnamamitra: String = "",
                      @JsonProperty("intsucceed")
                      val intsucceed: Int = 0,
                      @JsonProperty("strnamakontrak")
                      val strnamakontrak: String = "",
                      @JsonProperty("strnamaorg")
                      val strnamaorg: String = "",
                      @JsonProperty("errdescription")
                      val errdescription: String = "",
                      @JsonProperty("dttoc")
                      val dttoc: String = "")


data class SearchContractModel(@JsonProperty("Request")
                               val request: String = "",
                               @JsonProperty("Error")
                               val error: String? = null,
                               @JsonProperty("Code")
                               val code: Int = 0,
                               @JsonProperty("Result")
                               val result: List<ResultItem>?)


