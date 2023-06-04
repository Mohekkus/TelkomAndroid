package com.telkom.capex.network.access

import com.fasterxml.jackson.annotation.JsonProperty

data class AppAccess(
    @JsonProperty("access")
    var access: String = "",

    @JsonProperty("p_intyear")
    var p_intyear: Int? = null,

    @JsonProperty("p_intmonthr")
    var p_intmonth: Int? = null,

    @JsonProperty("p_intidorg")
    var p_intidorg: Int? = null,

    @JsonProperty("pagenum")
    var pagenum: Int? = null,

    @JsonProperty("preview")
    var preview: Boolean? = null,

    @JsonProperty("search_for")
    var search_for: String? = null,

    @JsonProperty("p_intidkontrak")
    var p_intidkontrak: Int? = null

)