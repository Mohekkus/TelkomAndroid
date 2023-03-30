package com.telkom.capex.ui.menu.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telkom.capex.network.utility.ServiceHandler
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

    private val _dashboard = MutableLiveData<ServiceHandler<DashboardResponse>>()
    val dashboard : LiveData<ServiceHandler<DashboardResponse>>
    get() = _dashboard

    private val _bastYear = MutableLiveData<ServiceHandler<DashboardYearResponse>>()
    val bastYear : LiveData<ServiceHandler<DashboardYearResponse>>
    get() = _bastYear

    private var defaultYear = Calendar.getInstance().get(Calendar.YEAR)

    init {
        viewModelScope.launch {
            repo.apply {
//                getBarYear(defaultYear).let {
//                    _bastYear.postValue(ServiceHandler.loading(null))
//                    when {
//                        it.isSuccessful -> {
//                            _bastYear.postValue(ServiceHandler.success(it.body()))
//                        }
//                        else -> {
//                            _bastYear.postValue(ServiceHandler.error(it.errorBody().toString(), null))
//                        }
//                    }
//                }
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
            }
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _page = MutableLiveData(0)

    val page: LiveData<Int> = _page
    fun setPage(number: Int) {
        _page.value = number
    }

    private val _year = MutableLiveData(Calendar.getInstance().get(Calendar.YEAR))
    val year: LiveData<Int> = _year
    fun setYear(number: Int) {
        _year.value = number
        getDifferentYear()
    }

    fun getDifferentYear() {
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
}