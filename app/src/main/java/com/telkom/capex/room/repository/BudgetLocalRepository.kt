package com.telkom.capex.room.repository

import com.telkom.capex.room.entity.BudgetListDataEntity
import com.telkom.capex.room.entity.BudgetListMonthEntity
import com.telkom.capex.room.entity.BudgetListYearEntity
import com.telkom.capex.room.transaction.BudgetDAO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetLocalRepository @Inject constructor(
    private val budgetDAO: BudgetDAO
) {

    suspend fun insertYear(data: BudgetListYearEntity) = budgetDAO.insertYear(data)
    suspend fun getYear(year: Int) = budgetDAO.getYear(year)

    suspend fun insertMonth(data: BudgetListMonthEntity) = budgetDAO.insertMonth(data)
    suspend fun getMonth(month: Int, year: Int) = budgetDAO.getMonth(month, year)
    suspend fun getAllMonth(month: Int) = budgetDAO.getAllMonth(month)

    suspend fun insertData(dataList: BudgetListDataEntity) = budgetDAO.insertData(dataList)
    suspend fun getAllData(month: Int, year: Int) = budgetDAO.getAllData(month, year)
}