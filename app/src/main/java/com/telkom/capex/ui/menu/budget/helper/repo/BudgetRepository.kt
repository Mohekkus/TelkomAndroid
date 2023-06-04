package com.telkom.capex.ui.menu.budget.helper.repo

import com.telkom.capex.network.access.AccessComposer
import com.telkom.capex.network.access.AppUpdateComposer
import com.telkom.capex.network.services.BudgetService
import com.telkom.capex.network.access.BudgetUpdateAccess
import javax.inject.Inject

class BudgetRepository @Inject constructor(private val budgetService: BudgetService) {

    suspend fun getListBudget(month: Int, year: Int, page: Int) =
        budgetService.getBudget(
            AccessComposer.getBudgetList(
                "api_select_budget_list",
                year, page, month + 1
            )
        )

    suspend fun getDetailBudget(id: Int) =
        budgetService.getDetailContract(
            AccessComposer.getDetailContract(
                "api_infokontrak_budget_new",
                id
            )
        )

    suspend fun getMonthly(id: Int, month: Int, year: Int) =
        budgetService.getDetailMonthly(
            AccessComposer.getDetailSmile(
                "api_budget_detail_bulanan",
                id, month, year
            )
        )

    suspend fun getSmile(id: Int, month: Int, year: Int) =
        budgetService.getDetailSmile(
            AccessComposer.getDetailSmile(
                "api_budget_bulanan_smile_new",
                id, month, year
            )
        )

    fun setupUpdateData(id: Int, nikon: Long, year: Int, status: Boolean): BudgetUpdateAccess {
        return AppUpdateComposer.getAccess(
            "api_kontrak_budget_update_new",
            id, nikon, year, status
        )
    }

    suspend fun initUpdateData(access: BudgetUpdateAccess) =
        budgetService.updateDetail(
            access
        )
}