package com.telkom.capex.ui.menu.budget.helper.repo

import com.telkom.capex.network.access.AccessComposer
import com.telkom.capex.network.services.BudgetService
import javax.inject.Inject

class BudgetRepository @Inject constructor(private val budgetService: BudgetService) {

    suspend fun getListBudget(year: Int, page: Int) =
        budgetService.getBudget(
            AccessComposer.getBudgetList(
                "api_all_kontrak_tahun",
                year, page
            )
        )
}