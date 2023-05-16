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

    fun getBudgetList(string: String, year: Int, page: Int): AppAccess {
        return getAccess(string).apply {
            p_intyear = year
            pagenum = 1
        }
    }

    fun getDoc(string: String, page: Int): AppAccess {
        return getAccess(string).apply {
            pagenum = page
        }
    }

    fun searchDoc(string: String, page: Int, query: String): AppAccess {
        return getDoc(string, page).apply {
            search_for = query
        }
    }

    fun detailDoc(string: String, query: String): AppAccess {
        return getAccess(string).apply {
            search_for = query
        }
    }

    fun getSearchContract(string: String, query: String): AppAccess {
        return getAccess(string).apply {
            search_for = query
        }
    }
}