package com.telkom.capex.ui.menu.tracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telkom.capex.network.utility.ServiceHandler
import com.telkom.capex.ui.menu.tracker.fragments.doc.model.DocResponse
import com.telkom.capex.ui.menu.tracker.fragments.doc.repo.DOCRepository
import com.telkom.capex.ui.menu.tracker.model.DOCSelectedModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(private val repo: DOCRepository) : ViewModel() {


    private val _doc = MutableLiveData<ServiceHandler<DocResponse>>()
    val doc : LiveData<ServiceHandler<DocResponse>>
        get() = _doc

    init {
        viewModelScope.launch {
            repo.getDOC().let {
                _doc.postValue(ServiceHandler.loading(null))
                when {
                    it.isSuccessful -> {
                        _doc.postValue(ServiceHandler.success(it.body()))
                        it.body()?.let { it1 -> setArchive(it1) }
                    }
                    else -> {
                        _doc.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                    }
                }
            }
        }
    }

    private val _archive = MutableLiveData<DocResponse>()
    val archive : LiveData<DocResponse>
        get() = _archive

    private fun setArchive(serviceHandler: DocResponse) {
        _archive.value = serviceHandler
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private val _selectedFilter = MutableLiveData<Int>().apply {
        value = 0
    }
    val selectedFilter: LiveData<Int> = _selectedFilter
    fun setSelectedFilter(number: Int) {
        _selectedFilter.value = number
    }


    private val _selectingItemList = MutableLiveData<Boolean>().apply {
        value = false
    }
    val selectingItemList: LiveData<Boolean> = _selectingItemList
    fun setSelectingItemList(selecting: Boolean) {
        _selectingItemList.value = selecting
    }

    private val _selectedItemList = MutableLiveData<MutableList<DOCSelectedModel>>().apply {
        value = mutableListOf()
    }
    val selectedItemList: LiveData<MutableList<DOCSelectedModel>> = _selectedItemList
    fun getCountSelectedItemList() : Int = selectedItemList.value?.size ?: 0
    fun setSelectedItemList(number: DOCSelectedModel) {
        _selectedItemList.value?.apply {
            if (this.any { it.getTitle() == number.getTitle()}) {
                remove(number)
            } else {
                add(number)
            }

        }

        if (selectedItemList.value.isNullOrEmpty())
            setSelectingItemList(false)
        else
            setSelectingItemList(true)
//            if (getCountSelectedItemList() == 1)
//                Log.e("Empty list?", "Yes Less")
//            else
//                Log.e("Empty list?", "No")
    }

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchFor(pattern: String) {
        val currentPattern = searchQuery.value

        if (currentPattern != pattern) return

        setSearchQuery(pattern)

        viewModelScope.launch {
            repo.getDOCSearch(pattern).let {
                _doc.postValue(ServiceHandler.loading(null))
                when {
                    it.isSuccessful -> {
                        _doc.postValue(ServiceHandler.success(it.body()))
                    }
                    else -> {
                        _doc.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                    }
                }
            }
        }
    }


    //DOC Detail DATA
    private val _selected = MutableLiveData<String>()
    val selected : LiveData<String>
        get() = _selected
    fun setSelected(contractName: String) {
        _selected.value = contractName
    }

    private val _data = MutableLiveData<ServiceHandler<DocResponse>>()
    val data : LiveData<ServiceHandler<DocResponse>>
        get() = _data

    private val _progress = MutableLiveData<Int>()
    val progress : LiveData<Int>
        get() = _progress

    fun setProgress(progress: Int) {
        _progress.value = progress
    }

    fun getData(query: String) {
        viewModelScope.launch {
            repo.getDOCDetail(query).let {
                _data.postValue(ServiceHandler.loading(null))
                if (it.isSuccessful) {
                    _data.postValue(ServiceHandler.success(it.body()))
                } else {
                    _data.postValue(ServiceHandler.error(it.errorBody().toString(), null))
                }
            }
        }
    }
}