package com.telkom.capex.ui.menu.budget.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.network.access.AppUpdateComposer.setApr
import com.telkom.capex.network.access.AppUpdateComposer.setAug
import com.telkom.capex.network.access.AppUpdateComposer.setDes
import com.telkom.capex.network.access.AppUpdateComposer.setFeb
import com.telkom.capex.network.access.AppUpdateComposer.setJan
import com.telkom.capex.network.access.AppUpdateComposer.setJuly
import com.telkom.capex.network.access.AppUpdateComposer.setJune
import com.telkom.capex.network.access.AppUpdateComposer.setMar
import com.telkom.capex.network.access.AppUpdateComposer.setMay
import com.telkom.capex.network.access.AppUpdateComposer.setNov
import com.telkom.capex.network.access.AppUpdateComposer.setOct
import com.telkom.capex.network.access.AppUpdateComposer.setSept
import com.telkom.capex.network.access.BudgetUpdateAccess
import com.telkom.capex.network.utility.ServiceHandler
import com.telkom.capex.room.entity.BudgetListDataEntity
import com.telkom.capex.room.entity.BudgetListMonthEntity
import com.telkom.capex.room.entity.BudgetListYearEntity
import com.telkom.capex.room.repository.BudgetLocalRepository
import com.telkom.capex.ui.menu.budget.helper.model.BudgetDetailMonthlyResponse
import com.telkom.capex.ui.menu.budget.helper.model.BudgetDetailResponse
import com.telkom.capex.ui.menu.budget.helper.model.BudgetDetailSmileResponse
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResponse
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResultItem
import com.telkom.capex.ui.menu.budget.helper.model.MonthlyDataItem
import com.telkom.capex.ui.menu.budget.helper.repo.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Year
import javax.inject.Inject

@HiltViewModel
class BudgetSharedViewModel @Inject constructor(
    private val repo: BudgetRepository,
    private val local: BudgetLocalRepository
    ) : ViewModel() {

    private var _data = MutableLiveData<ServiceHandler<BudgetListResponse>>()
    val data: LiveData<ServiceHandler<BudgetListResponse>>
        get() = _data

    private var _setData = MutableLiveData<MutableList<BudgetListDataEntity?>>()
    val setData: LiveData<MutableList<BudgetListDataEntity?>>
        get() = _setData

    init {
        viewModelScope.launch {
            repo.getListBudget(MonthModifier.currentMonthInt(), Year.now().value, 1).let {
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

    private val _year = MutableLiveData<Int> ().apply {
        value = Year.now().value
    }
    val year: LiveData<Int>
        get() = _year

    fun setYear(year: Int) {
        _year.value = year
    }

    private val _monthList = MutableLiveData<Int>().apply {
        value = MonthModifier.currentMonthInt()
    }
    val monthList: LiveData<Int> = _monthList
    fun setMonthList(data: Int) {
        println("pos: $data")
        _monthList.value = data
    }

    private val _setting = MutableLiveData<Boolean> ().apply {
        value = false
    }
    val setting: LiveData<Boolean>
        get() = _setting
    fun setSetting(value: Boolean) {
        _setting.value = value
    }

    private val _editting = MutableLiveData<Boolean> ().apply {
        value = false
    }
    val editting: LiveData<Boolean>
        get() = _editting
    fun setEditting(value: Boolean) {
        _editting.value = value
    }

    private val _selecting = MutableLiveData<Int> ().apply {
        value = 0
    }
    val selecting: LiveData<Int>
        get() = _selecting
    fun selecting(value: Int) {
        _selecting.value = value
    }

    private val _enableListener = MutableLiveData<Boolean> ().apply {
        value = false
    }
    val enableListener: LiveData<Boolean>
        get() = _enableListener
    fun setEnableListener(value: Boolean) {
        _enableListener.value = value
    }

    private var _detail = MutableLiveData<ServiceHandler<BudgetDetailResponse>>()
    val detail: LiveData<ServiceHandler<BudgetDetailResponse>>
        get() = _detail

    private var detailId: Int? = null
    fun getDetailContract(id: Int) {
        viewModelScope.launch {
            repo.getDetailBudget(id).let {
                _detail.postValue(ServiceHandler.loading(null))
                when {
                    it.isSuccessful -> {
                        getMonthlyData(id)
                        detailId = id
                        _detail.postValue(ServiceHandler.success(it.body()))
                    }
                    else -> {
                        _detail.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                    }
                }
            }
        }
    }

    private var _monthlyData = MutableLiveData<ServiceHandler<BudgetDetailMonthlyResponse>>()
    val monthlyData: LiveData<ServiceHandler<BudgetDetailMonthlyResponse>>
        get() = _monthlyData

    private fun getMonthlyData(id: Int) {
        viewModelScope.launch {
            repo.getMonthly(id, monthList.value ?: MonthModifier.currentMonthInt(), year.value ?: Year.now().value)
                .let {
                    _monthlyData.postValue(ServiceHandler.loading(null))
                    when {
                        it.isSuccessful -> {
                            _monthlyData.postValue(ServiceHandler.success(it.body()))
                        }
                        else -> {
                            _monthlyData.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                        }
                    }
                }
        }
    }


    private var _smile = MutableLiveData<ServiceHandler<BudgetDetailSmileResponse>>()
    val smile: LiveData<ServiceHandler<BudgetDetailSmileResponse>>
        get() = _smile

    fun getSmile() {
        viewModelScope.launch {
            repo.getSmile(
                1,
                monthList.value ?: MonthModifier.currentMonthInt(),
                year.value ?: Year.now().value
            ).let {
                _smile.postValue(ServiceHandler.loading(null))
                when {
                    it.isSuccessful -> {
                        _smile.postValue(ServiceHandler.success(it.body()))
                    }
                    else -> {
                        _smile.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                    }
                }
            }
        }
    }

    private var _isRefreshing = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing
    fun setRefreshing(boolean: Boolean) {
        _isRefreshing.value = boolean
    }

    fun addListData(year: Int?, month: Int?, data: BudgetListResultItem?) {
        if (year == null || month == null || data == null) return
        viewModelScope.launch {
            local.apply {
                val availableYear = getYear(year)
                val availableMonth = getMonth(month, year)
                val availableData = getAllData(month, year)

                if (availableYear == null)
                    insertYear(
                        BudgetListYearEntity(
                            year = year
                        )
                    )

                if (availableMonth == null)
                    insertMonth(
                        BudgetListMonthEntity(
                            month = month,
                            yearReference = year
                        )
                    )

                val percentage = listOf(
                    Pair("RKAP", data.perplaningpm),
                    Pair("PM", data.perplaningrkap)
                )
                val param =
                    BudgetListDataEntity(
                        planningPM = data.totalplaningpm,
                        actual = data.totalactual,
                        planningRKAP = data.totalplaningrkap,
                        data = data.jsonArray,
                        percentage = percentage,
                        monthReferences = month
                    )

                if (availableData == null)
                    insertData(
                        param
                    )

                getDataList()
            }
        }
    }

    fun getDataList() {
        if (setData.value?.groupBy { it?.monthReferences }?.containsKey(monthList.value) == true) return
        viewModelScope.launch {
            local.getAllData(
                monthList.value ?: MonthModifier.currentMonthInt(),
                year.value ?: Year.now().value
            ).let { listDataEntityList ->
                if (listDataEntityList == null) getFromAPI(
                    monthList.value ?: MonthModifier.currentMonthInt(),
                    year.value ?: Year.now().value
                )
                else
                    when {
                        setData.value == null -> _setData.value = mutableListOf(listDataEntityList)
                        setData.value?.groupBy { it?.monthReferences }?.containsKey(monthList.value) == false -> {
                            val tempSet =
                                _setData.value!!.apply {
                                    add(listDataEntityList)
                                }
                            _setData.value = tempSet
                        }
                        else -> return@launch
                    }
            }
        }
    }

    fun getFromAPI(month: Int, year: Int) {
        viewModelScope.launch {
            repo.getListBudget(month, year, 1).let {
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

    private var _copiedData = MutableLiveData<Pair<Int, MonthlyDataItem>>()
    val copiedData: LiveData<Pair<Int, MonthlyDataItem>>
        get() = _copiedData

    fun copyData(data: MonthlyDataItem?, position: Int) {
        if (data == null) return
        val dataset = data

        _copiedData.value = Pair(position, MonthlyDataItem(
            planningPM =  data.planningPM,
            planningRKAP = data.planningRKAP,
            actual = data.actual
        )
        )
    }

    fun updatedCopiedData(position: Int, value: Long) {
        _copiedData.value?.second?.apply {
            when (position) {
                0 ->
                    planningPM = value
                1 ->
                    planningRKAP = value
                2 ->
                    actual = value
            }
        }
    }

    private var _listCopiedData = MutableLiveData<MutableList<Pair<Int, MonthlyDataItem>?>>()
        .apply {
            value = mutableListOf()
        }
    val listCopiedData: LiveData<MutableList<Pair<Int, MonthlyDataItem>?>>
        get() = _listCopiedData
    fun settleCopiedData() {
        if (copiedData.value == null) return

        if (listCopiedData.value === null) _listCopiedData.value = mutableListOf(copiedData.value)
        else {
            val temp = _listCopiedData.value?.apply {
                add(copiedData.value)
            }
            _listCopiedData.value = temp
        }
    }

    fun revertChanges() {
        if (listCopiedData.value != null)
            _listCopiedData.value = null
    }
    fun appliedChanges() {
        if (listCopiedData.value == null) return

        val data = detail.value?.data?.result?.get(0)
        val status = data?.boolstatus
        val default = monthlyData.value?.data?.result?.get(0)?.mdata
        val setup = repo.setupUpdateData(
            detailId ?: 0,
            data?.bintnilaikontrak ?: 0L,
            year.value ?: Year.now().value,
            status ?: true
        ).also {
            default?.forEachIndexed { index, monthlyDataItem ->
                monthlyDataItem.apply {
                    when (index) {
                        0 -> setJan(it, actual, planningPM, planningRKAP)
                        1 -> setFeb(it, actual, planningPM, planningRKAP)
                        2 -> setMar(it, actual, planningPM, planningRKAP)
                        3 -> setApr(it, actual, planningPM, planningRKAP)
                        4 -> setMay(it, actual, planningPM, planningRKAP)
                        5 -> setJune(it, actual, planningPM, planningRKAP)
                        6 -> setJuly(it, actual, planningPM, planningRKAP)
                        7 -> setAug(it, actual, planningPM, planningRKAP)
                        8 -> setSept(it, actual, planningPM, planningRKAP)
                        9 -> setOct(it, actual, planningPM, planningRKAP)
                        10 -> setNov(it, actual, planningPM, planningRKAP)
                        11 -> setDes(it, actual, planningPM, planningRKAP)
                    }
                }
            }
        }

        if (listCopiedData.value.isNullOrEmpty()) return

        listCopiedData.value?.map {
            setup.also { dataItem ->
                it?.second?.apply {
                    when (it.first) {
                        0 -> setJan(dataItem, actual, planningPM, planningRKAP)
                        1 -> setFeb(dataItem, actual, planningPM, planningRKAP)
                        2 -> setMar(dataItem, actual, planningPM, planningRKAP)
                        3 -> setApr(dataItem, actual, planningPM, planningRKAP)
                        4 -> setMay(dataItem, actual, planningPM, planningRKAP)
                        5 -> setJune(dataItem, actual, planningPM, planningRKAP)
                        6 -> setJuly(dataItem, actual, planningPM, planningRKAP)
                        7 -> setAug(dataItem, actual, planningPM, planningRKAP)
                        8 -> setSept(dataItem, actual, planningPM, planningRKAP)
                        9 -> setOct(dataItem, actual, planningPM, planningRKAP)
                        10 -> setNov(dataItem, actual, planningPM, planningRKAP)
                        11 -> setDes(dataItem, actual, planningPM, planningRKAP)
                        else -> {}
                    }
                }
            }
        }

        initiateUpdate(setup)
    }

    private var _changesLog = MutableLiveData<String>()
    val changesLog: LiveData<String>
        get() = _changesLog

    fun setChangeLog(string: String) {
        _changesLog.value = string
    }

    private fun initiateUpdate(setup: BudgetUpdateAccess) {
        viewModelScope.launch {
            _changesLog.value = "Submitting Changes"
            repo.initUpdateData(setup).let {
                if (it.isSuccessful)
                    _changesLog.value = "Sent and updated"
                else
                    _changesLog.value = it.body()?.result?.get(0)?.error ?: it.body()?.error
            }
        }
    }
}