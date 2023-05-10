package com.telkom.capex.ui.menu.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.telkom.capex.etc.MonthModifier
import java.util.*

class BudgetViewModel : ViewModel() {

    private val _monthList = MutableLiveData<Int>().apply {
        value = 0
    }
    val monthList: LiveData<Int> = _monthList
    fun setMonthList(data: Int) {
        _monthList.value = data
    }
}