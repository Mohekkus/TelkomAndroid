package com.telkom.capex.ui.menu.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telkom.capex.network.utility.ServiceHandler
import com.telkom.capex.ui.menu.search.model.SearchContractModel
import com.telkom.capex.ui.menu.search.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FragmentSearchViewModel @Inject constructor(
    private val search: SearchRepository
): ViewModel() {


    private val _result = MutableLiveData<ServiceHandler<SearchContractModel>>()
    val result : LiveData<ServiceHandler<SearchContractModel>>
        get() = _result

    init {
        viewModelScope.launch{
            insertIntoResult(search.getSearch(""))
        }
    }

    private val _query = MutableLiveData<String>().apply {
        value = ""
    }

    fun getQuery() = _query
    fun setQuery(query: String) {
        _query.value = query
    }

    fun postQuery() {
        if (!_query.value.isNullOrEmpty())
            viewModelScope.launch {
                insertIntoResult(search.getSearch(_query.value.toString()))
            }
    }


    private fun insertIntoResult(it: Response<SearchContractModel>) {
        _result.postValue(ServiceHandler.loading(null))
        when {
            it.isSuccessful -> {
                _result.postValue(ServiceHandler.success(it.body()))
            }
            else -> {
                _result.postValue(ServiceHandler.error(it.errorBody().toString(), null))
            }
        }
    }
}