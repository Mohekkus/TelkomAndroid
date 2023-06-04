package com.telkom.capex.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BudgetListMonthEntity(
    @PrimaryKey(autoGenerate = true) val serial: Int = 0,
    @ColumnInfo("yearReference") val yearReference: Int,
    @ColumnInfo("month") val month: Int,
)
