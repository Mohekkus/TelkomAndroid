package com.telkom.capex.ui.menu.budget.helper.model

class BudgetListContainer(
    val year: Int = 0,
    val listOnYear: List<BudgetListMonthContainer>? = null
)

class BudgetListMonthContainer(
    val month: Int = 0,
    val listOnMonth: List<BudgetListResultItem>? = null
)
