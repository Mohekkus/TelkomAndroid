package com.telkom.capex.room.relationship.onetomany

import androidx.room.Embedded
import androidx.room.Relation
import com.telkom.capex.room.entity.BudgetListDataEntity
import com.telkom.capex.room.entity.BudgetListMonthEntity

data class MonthAndData(
    @Embedded val month: BudgetListMonthEntity,
    @Relation(
        parentColumn = "serial",
        entityColumn = "monthReferences"
    )
    val listMonth: List<BudgetListDataEntity>
)
