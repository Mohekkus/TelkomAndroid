package com.telkom.capex.menu.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.telkom.capex.etc.MonthModifier
import java.util.*

class BudgetViewModel : ViewModel() {

    private val _month = MutableLiveData<String>().apply {
        value = MonthModifier.getMonth(
            getCurrentMonth()
        )
    }
    val month: LiveData<String> = _month
    fun setMonth(string: String) {
        _month.value = string
    }

    private fun getCurrentMonth(): Int = Calendar.getInstance().get(Calendar.MONTH)

    private val _page = MutableLiveData<Int>().apply {
        value = 0
    }
    val page = _page
    fun setPage(number: Int) {
        _page.value = number
    }

    private val _monthList = MutableLiveData<Int>().apply {
        value = 0
    }
    val monthList: LiveData<Int> = _monthList
    fun setMonthList(data: Int) {
        _monthList.value = data
    }

    private val _posItem = MutableLiveData<MutableList<Float>>().apply {
        value = mutableListOf<Float>().apply {
            add(0f)
            add(0f)
        }
    }
    val posItem: LiveData<MutableList<Float>> = _posItem
    fun setPostItem(x: Float, y: Float) {
        _posItem.value = mutableListOf<Float>().apply {
            add(x)
            add(y)
        }
    }
}