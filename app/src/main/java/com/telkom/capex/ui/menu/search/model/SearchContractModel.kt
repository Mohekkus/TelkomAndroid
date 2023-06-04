package com.telkom.capex.ui.menu.search.model


import com.fasterxml.jackson.annotation.JsonProperty

data class SearchContractResultItem(@JsonProperty("intidkontrak")
                      val intidkontrak: Int = 0,
                      @JsonProperty("strnamamitra")
                      val strnamamitra: String = "",
                      @JsonProperty("strnamakontrak")
                      val strnamakontrak: String = "",
                      @JsonProperty("strnamaorg")
                      val strnamaorg: String = "",
                      @JsonProperty("plan")
                      val plan: Long = 0L,
                      @JsonProperty("status")
                      val status: Boolean = false,
                      @JsonProperty("target")
                      val target: Long = 0L)


data class SearchContractModel(@JsonProperty("Request")
                               val request: String = "",
                               @JsonProperty("Error")
                               val error: String? = null,
                               @JsonProperty("Code")
                               val code: Int = 0,
                               @JsonProperty("Result")
                               val result: List<SearchContractResultItem>?)


