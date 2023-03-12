package com.telkom.capex.ui.menu.dashboard.helper.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ResultItem(@JsonProperty("bintsumbast")
                      val bastTotal: Long = 0,
                      @JsonProperty("intperbastdone")
                      val bastDone: Long = 0,
                      @JsonProperty("intsucceed")
                      val code: Int = 0,
                      @JsonProperty("intpersisapekerjaan")
                      val remainJob: Long = 0,
                      @JsonProperty("errdescription")
                      val err: String = "",
                      @JsonProperty("bintsumnikon")
                      val nikon: Long = 0)