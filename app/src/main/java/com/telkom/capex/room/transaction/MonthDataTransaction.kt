package com.telkom.capex.room.transaction

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.telkom.capex.room.relationship.onetomany.MonthAndData
import com.telkom.capex.room.relationship.onetomany.YearAndMonth

@Dao
interface MonthDataTransaction {

    @Transaction
    @Query("SELECT * FROM BudgetListMonthEntity")
    fun getYearAndMonth(): List<MonthAndData>
}