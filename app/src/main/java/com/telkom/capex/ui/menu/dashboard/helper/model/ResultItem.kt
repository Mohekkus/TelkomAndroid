package com.telkom.capex.ui.menu.dashboard.helper.model


import com.fasterxml.jackson.annotation.JsonProperty

data class ResultItem(@JsonProperty("bintsumbast")
                      val bintsumbast: Long = 0,
                      @JsonProperty("intperbastdone")
                      val intperbastdone: Long = 0,
                      @JsonProperty("intsucceed")
                      val intsucceed: Long = 0,
                      @JsonProperty("bintsisapekerjaan")
                      val bintsisapekerjaan: Long = 0,
                      @JsonProperty("intpersisapekerjaan")
                      val intpersisapekerjaan: Long = 0,
                      @JsonProperty("errdescription")
                      val errdescription: String = "",
                      @JsonProperty("bintsumnikon")
                      val bintsumnikon: Long = 0,
                      @JsonProperty("intcountkont")
                      val intcountkont: Long = 0)


