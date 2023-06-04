package com.telkom.capex.room.transaction

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.telkom.capex.room.relationship.onetomany.YearAndMonth

@Dao
interface YearMonthTransaction {

    @Transaction
    @Query("SELECT * FROM BudgetListYearEntity")
    fun getYearAndMonth(): List<YearAndMonth>
}