package com.telkom.capex.ui.menu.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telkom.capex.network.utility.ServiceHandler
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardDivisionResponse
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardPieChartResponse
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardResponse
import com.telkom.capex.ui.menu.dashboard.helper.model.DashboardYearResponse
import com.telkom.capex.ui.menu.dashboard.helper.repo.DashboardRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: DashboardRepo
) : ViewModel() {

    private val defaultYear = Calendar.getInstance().get(Calendar.YEAR)
    private val _year = MutableLiveData(Calendar.getInstance().get(Calendar.YEAR))
    private val _page = MutableLiveData(0)

    private val _dashboard = MutableLiveData<ServiceHandler<DashboardResponse>>()
    private val _bastYear = MutableLiveData<ServiceHandler<DashboardYearResponse>>()
    private val _division = MutableLiveData<ServiceHandler<DashboardDivisionResponse>>()
    private val _pie = MutableLiveData<ServiceHandler<DashboardPieChartResponse>>()
    private val _pieIdHolder = MutableLiveData<Int>()

    private var _isRefreshing = MutableLiveData<Boolean>().apply {
        value = false
    }

    val year: LiveData<Int> = _year
    val page: LiveData<Int> = _page

    val dashboard : LiveData<ServiceHandler<DashboardResponse>>
        get() = _dashboard
    val bastYear : LiveData<ServiceHandler<DashboardYearResponse>>
        get() = _bastYear
    val division : LiveData<ServiceHandler<DashboardDivisionResponse>>
        get() = _division
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing
    val pie : LiveData<ServiceHandler<DashboardPieChartResponse>>
        get() = _pie

    init {
        getDashboard()
    }

    fun setRefreshing(boolean: Boolean) {
        _isRefreshing.value = boolean
    }

    fun setPage(number: Int) {
        _page.value = number
    }

    fun setYear(number: Int) {
        _year.value = number
        getDifferentYear()
    }

    private fun getDifferentYear() {
        viewModelScope.launch {
            repo.getBarYear(year.value?.toInt() ?: defaultYear).let {
                _bastYear.postValue(ServiceHandler.loading(null))
                when {
                    it.isSuccessful -> {
                        _bastYear.postValue(ServiceHandler.success(it.body()))
                    }
                    else -> {
                        _bastYear.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                    }
                }
            }
        }
    }

    fun getPie(id: Int) {
        viewModelScope.launch {
            _pieIdHolder.apply {
                if (value == id) return@launch
                if (value == null) value = id
            }
            repo.getPie(id, true).let {
                _pie.postValue(ServiceHandler.loading(null))
                when {
                    it.isSuccessful -> {
                        _pie.postValue(ServiceHandler.success(it.body()))
                    }
                    else -> {
                        _pie.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                    }
                }
            }
        }
    }

    fun getDashboard() {
        viewModelScope.launch {
            repo.apply {
                getDashboard().let {
                    _dashboard.postValue(ServiceHandler.loading(null))
                    when {
                        it.isSuccessful -> {
                            _dashboard.postValue(ServiceHandler.success(it.body()))
                        }
                        else -> {
                            _dashboard.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                        }
                    }
                }
                getDivision().let {
                    _division.postValue(ServiceHandler.loading(null))
                    when {
                        it.isSuccessful -> {
                            _division.postValue(ServiceHandler.success(it.body()))
                        }
                        else -> {
                            _division.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                        }
                    }
                }
                getBarYear(defaultYear).let {
                    _bastYear.postValue(ServiceHandler.loading(null))
                    when {
                        it.isSuccessful -> {
                            _bastYear.postValue(ServiceHandler.success(it.body()))
                        }
                        else -> {
                            _bastYear.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                        }
                    }
                }
            }
        }
    }
}