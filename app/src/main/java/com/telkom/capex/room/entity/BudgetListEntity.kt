package com.telkom.capex.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Year

@Entity
data class BudgetListYearEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("year") val year: Int
)
