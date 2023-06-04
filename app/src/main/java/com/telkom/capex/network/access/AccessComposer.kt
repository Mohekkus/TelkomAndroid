package com.telkom.capex.network.access

import java.util.Calendar

object AccessComposer {

    fun getAccess(string: String): AppAccess {
        return AppAccess().apply {
            access = string
        }
    }


    fun getPie(string: String, id: Int, isPreview: Boolean): AppAccess {
        return AppAccess().apply {
            access = string
            p_intyear = Calendar.getInstance().get(Calendar.YEAR)
            p_intidorg = id
            preview = isPreview
        }
    }
    fun getBarYear(string: String, year: Int): AppAccess {
        return getAccess(string).apply {
            p_intyear = year
        }
    }

    fun getBudgetList(string: String, year: Int, page: Int, month: Int): AppAccess {
        return getAccess(string).apply {
            p_intyear = year
            p_intmonth = month
            pagenum = page
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

    fun getDetailContract(string: String, id: Int): AppAccess {
        return getAccess(string).apply {
            p_intidkontrak = id
        }
    }
    fun getDetailSmile(string: String, id: Int, month: Int, year: Int): AppAccess {
        return AppAccess().apply {
            access = string
            p_intyear = year
            p_intmonth = month
            p_intidkontrak = id
        }
    }
}