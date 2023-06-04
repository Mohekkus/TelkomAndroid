package com.telkom.capex.room.relationship.onetomany

import androidx.room.Embedded
import androidx.room.Relation
import com.telkom.capex.room.entity.BudgetListYearEntity
import com.telkom.capex.room.entity.BudgetListMonthEntity


data class YearAndMonth(
    @Embedded val year: BudgetListYearEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "yearReference"
    )
    val listMonth: List<BudgetListMonthEntity>
)
