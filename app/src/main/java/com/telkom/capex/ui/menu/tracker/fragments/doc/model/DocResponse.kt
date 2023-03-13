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
                      val contractDescription: String? = null,
                      @JsonProperty("txtextendinfo")
                      val contractDocDescription: String? = null,
                      @JsonProperty("dtdatepengajuan")
                      val contractDate: String? = null,
                      @JsonProperty("intstatus")
                      val contractProgress: Int = 0,
                      @JsonProperty("boolstatus")
                      val contractStatus: Boolean = false,
                      @JsonProperty("intsucceed")
                      val code: Int = 0,
                      @JsonProperty("strnamakontrak")
                      val contractName: String = "",
                      @JsonProperty("strnamamitra")
                      val contractPartner: String? = null,
                      @JsonProperty("strnamaorg")
                      val contractUnit: String? = null,
                      @JsonProperty("dtedc")
                      val contractEDC: String? = null,
                      @JsonProperty("dttoc")
                      val contractTOC: String? = null,
                      @JsonProperty("errdescription")
                      val err: String = "")


