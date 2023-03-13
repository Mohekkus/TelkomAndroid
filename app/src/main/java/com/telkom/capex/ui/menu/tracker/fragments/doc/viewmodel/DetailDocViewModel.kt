package com.telkom.capex.ui.menu.tracker.fragments.doc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telkom.capex.network.utility.ServiceHandler
import com.telkom.capex.ui.login.data.model.AuthTokenResponse
import com.telkom.capex.ui.menu.tracker.fragments.doc.model.DocResponse
import com.telkom.capex.ui.menu.tracker.fragments.doc.repo.DOCRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailDocViewModel @Inject constructor(private val repo: DOCRepository) : ViewModel() {

    private val _data = MutableLiveData<ServiceHandler<DocResponse>>()
    val data : LiveData<ServiceHandler<DocResponse>>
        get() = _data

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