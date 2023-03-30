package com.telkom.capex.ui.menu.budget.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telkom.capex.network.utility.ServiceHandler
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResponse
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResultItem
import com.telkom.capex.ui.menu.budget.helper.repo.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Year
import javax.inject.Inject

@HiltViewModel
class BudgetingSharedViewModel @Inject constructor(private val repo: BudgetRepository): ViewModel() {

    private var _data = MutableLiveData<ServiceHandler<BudgetListResponse>>()
    val data: LiveData<ServiceHandler<BudgetListResponse>>
        get() = _data

    private var _listData = MutableLiveData<List<BudgetListResultItem>>()
    val listData: LiveData<List<BudgetListResultItem>>
    get() = _listData

    init {
//        viewModelScope.launch {
//            repo.getListBudget(Year.now().value, 1).let {
//                _data.postValue(ServiceHandler.loading(null))
//                when {
//                    it.isSuccessful -> {
//                        _data.postValue(ServiceHandler.success(it.body()))
//                    }
//                    else -> {
//                        _data.postValue(ServiceHandler.error(it.errorBody().toString(), null))
//                    }
//                }
//            }
//        }
    }

    fun getListOnPage(page: Int) {
        viewModelScope.launch {
            repo.getListBudget(year.value ?: Year.now().value, page).let {
                _data.postValue(ServiceHandler.loading(null))
                when {
                    it.isSuccessful -> {
                        _data.postValue(ServiceHandler.success(it.body()))
                    }
                    else -> {
                        _data.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                    }
                }
            }
        }
    }

    fun addListData(result: List<BudgetListResultItem>) {
        _listData.postValue(
            (listData.value?.plus(result) ?: listData.value)
        )
    }

    private val _monthList = MutableLiveData<Int>().apply {
        value = 0
    }
    val monthList: LiveData<Int> = _monthList
    fun setMonthList(data: Int) {
        _monthList.value = data
    }

    private val _year = MutableLiveData<Int> ()
    val year: LiveData<Int>
        get() = _year

    fun setYear(year: Int) {
        _year.value = year
    }
}