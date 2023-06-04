package com.telkom.capex.network.access

object AppUpdateComposer {

    fun getAccess(accessString: String, id: Int, nikon: Long, year: Int, status: Boolean): BudgetUpdateAccess {
        return BudgetUpdateAccess().apply {
            access = accessString
            p_intidkontrak = id
            p_intyear = year
            p_bintnilaikontrak = nikon
            p_boolstatus = status
        }
    }

    fun setJan(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmjan = actual
            p_bintbastplaningpmjan = pm
            p_bintplaingrkapjan = rkap
        }
    }

    fun setFeb(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmfeb = actual
            p_bintbastplaningpmfeb = pm
            p_bintplaingrkapfeb = rkap
        }
    }

    fun setMar(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmmar = actual
            p_bintbastplaningpmmar = pm
            p_bintplaingrkapmar = rkap
        }
    }

    fun setApr(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmapr = actual
            p_bintbastplaningpmapr = pm
            p_bintplaingrkapapr = rkap
        }
    }

    fun setMay(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmmay = actual
            p_bintbastplaningpmmay = pm
            p_bintplaingrkapmay = rkap
        }
    }

    fun setJune(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmjun = actual
            p_bintbastplaningpmjun = pm
            p_bintplaingrkapjun = rkap
        }
    }

    fun setJuly(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmjul = actual
            p_bintbastplaningpmjul = pm
            p_bintplaingrkapjul = rkap
        }
    }

    fun setAug(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmaug = actual
            p_bintbastplaningpmaug = pm
            p_bintplaingrkapaug = rkap
        }
    }

    fun setSept(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmsep = actual
            p_bintbastplaningpmsep = pm
            p_bintplaingrkapsep = rkap
        }
    }

    fun setOct(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmoct = actual
            p_bintbastplaningpmoct = pm
            p_bintplaingrkapoct = rkap
        }
    }

    fun setNov(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmnov = actual
            p_bintbastplaningpmnov = pm
            p_bintplaingrkapnov = rkap
        }
    }

    fun setDes(into: BudgetUpdateAccess, actual: Long, pm: Long, rkap: Long): BudgetUpdateAccess {
        return into.apply {
            p_bintbastactualpmdes = actual
            p_bintbastplaningpmdes = pm
            p_bintplaingrkapdes = rkap
        }
    }
}