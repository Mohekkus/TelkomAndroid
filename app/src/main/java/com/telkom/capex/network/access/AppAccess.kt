package com.telkom.capex.network.access

import com.fasterxml.jackson.annotation.JsonProperty

data class AppAccess(
    @JsonProperty("access")
    var access: String = "",

    @JsonProperty("p_intyear")
    var p_intyear: Int? = null,

    @JsonProperty("pagenum")
    var pagenum: Int? = null,

    @JsonProperty("search_for")
    var search_for: String = ""
)