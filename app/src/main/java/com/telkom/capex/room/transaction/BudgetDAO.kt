package com.telkom.capex.room.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.telkom.capex.room.entity.BudgetListDataEntity
import com.telkom.capex.room.entity.BudgetListMonthEntity
import com.telkom.capex.room.entity.BudgetListYearEntity
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResultItem
import com.telkom.capex.ui.menu.budget.helper.model.DaoListResultItem

@Dao
interface BudgetDAO {

    @Insert
    suspend fun insertYear(year: BudgetListYearEntity)

    @Query("SELECT * FROM BudgetListYearEntity")
    suspend fun getYear() : MutableList<BudgetListYearEntity>

    @Query("SELECT * FROM BudgetListYearEntity WHERE year = :year")
    suspend fun getYear(year: Int) : BudgetListYearEntity?

    @Insert
    suspend fun insertMonth(month: BudgetListMonthEntity)

    @Query("SELECT * " +
            "FROM BudgetListMonthEntity " +
            "WHERE month = :month")
    suspend fun getAllMonth(month: Int) : BudgetListMonthEntity?
    @Query("SELECT * FROM BudgetListMonthEntity month" +
            "   JOIN BudgetListYearEntity year ON month.yearReference = year.year" +
            "   WHERE yearReference = :year " +
            "   AND month = :month")
    suspend fun getMonth(month: Int, year: Int) : BudgetListMonthEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: BudgetListDataEntity)

    @Query("SELECT data.serial, yearEn.year, data.monthReferences, data.planningPM, data.planningRKAP, data.percentage, data.actual, data.data " +
            "FROM BudgetListDataEntity data" +
            "   JOIN BudgetListMonthEntity month ON data.monthReferences = month.month" +
            "   JOIN BudgetListYearEntity yearEn ON month.yearReference = yearEn.year" +
            "   WHERE month = :month AND year = :year")
    suspend fun getAllData(month: Int, year: Int): BudgetListDataEntity?
}