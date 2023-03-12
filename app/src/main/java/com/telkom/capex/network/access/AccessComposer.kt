package com.telkom.capex.network.access

object AccessComposer {

    fun getAccess(string: String): AppAccess {
        return AppAccess().apply {
            access = string
        }
    }

    fun getBarYear(string: String, year: Int): AppAccess {
        return getAccess(string).apply {
            p_intyear = year
        }
    }
}