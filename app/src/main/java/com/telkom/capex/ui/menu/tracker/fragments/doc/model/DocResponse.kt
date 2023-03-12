package com.telkom.capex.ui.menu.tracker.fragments.doc.model


import com.fasterxml.jackson.annotation.JsonProperty

data class DocResponse(@JsonProperty("Request")
                       val request: String = "",
                       @JsonProperty("Error")
                       val error: String? = null,
                       @JsonProperty("Code")
                       val code: Int = 0,
                       @JsonProperty("Result")
                       val result: List<ResultDOC>?)


data class ResultDOC(@JsonProperty("intidkontrak")
                      val idContract: Int = 0,
                      @JsonProperty("txtinfo")
                      val contractDescription: String = "",
                      @JsonProperty("intstatus")
                      val contractProgress: Int = 0,
                      @JsonProperty("boolstatus")
                      val contractStatus: Boolean = false,
                      @JsonProperty("strorg")
                      val contractUnit: String = "",
                      @JsonProperty("intsucceed")
                      val code: Int = 0,
                      @JsonProperty("strnamakontrak")
                      val contractName: String = "",
                      @JsonProperty("errdescription")
                      val err: String = "")


