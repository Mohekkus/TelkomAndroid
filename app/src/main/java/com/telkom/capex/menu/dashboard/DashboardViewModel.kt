package com.telkom.capex.menu.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _page = MutableLiveData(0)

    val page: LiveData<Int> = _page
    fun setPage(number: Int) {
        _page.value = number
    }

    private val _size = MutableLiveData(0)
    val size: LiveData<Int> = _size
    fun setSize(number: Int) {
        _size.value = number
    }

    private val _year = MutableLiveData(Calendar.getInstance().get(Calendar.YEAR))
    val year: LiveData<Int> = _year
    fun setYear(number: Int) {
        _year.value = number
    }
}