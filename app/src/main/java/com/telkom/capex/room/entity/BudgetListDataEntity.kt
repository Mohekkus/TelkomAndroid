package com.telkom.capex.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListJsonItem
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResultItem

@Entity
data class BudgetListDataEntity(
    @PrimaryKey(autoGenerate = true) var serial: Int = 0,
    @ColumnInfo("monthReferences") val monthReferences: Int,
    @ColumnInfo("actual") val actual: Long,
    @ColumnInfo("planningPM") val planningPM: Long,
    @ColumnInfo("planningRKAP") val planningRKAP: Long,
    @ColumnInfo("percentage") val percentage: List<Pair<String, Double?>>,
    @ColumnInfo("data") val data: List<BudgetListJsonItem>?
)
