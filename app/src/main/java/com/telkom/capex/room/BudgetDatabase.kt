package com.telkom.capex.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.telkom.capex.room.entity.BudgetListDataEntity
import com.telkom.capex.room.entity.BudgetListMonthEntity
import com.telkom.capex.room.entity.BudgetListYearEntity
import com.telkom.capex.room.etc.PojoTypeConverter
import com.telkom.capex.room.transaction.BudgetDAO
import com.telkom.capex.room.transaction.MonthDataTransaction
import com.telkom.capex.room.transaction.YearMonthTransaction

@Database(
    entities = [
        BudgetListYearEntity::class,
        BudgetListMonthEntity::class,
        BudgetListDataEntity::class
               ],
    version = 1
)
@TypeConverters(PojoTypeConverter::class)
abstract class BudgetDatabase: RoomDatabase() {
    abstract fun budgetDAO(): BudgetDAO
    abstract fun yearMonthTransaction(): YearMonthTransaction
    abstract fun monthDataTransaction(): MonthDataTransaction
}