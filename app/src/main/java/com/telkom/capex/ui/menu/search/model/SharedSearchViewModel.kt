package com.telkom.capex.ui.menu.search.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedSearchViewModel @Inject constructor() : ViewModel() {


    private val _flag = MutableLiveData<DICTIONARY>().apply {
        value = DICTIONARY.DEFAULT
    }

    fun getFlag() = _flag
    fun setFlagTo(param: DICTIONARY) {
        _flag.value = param
    }

    enum class DICTIONARY {
        BUDGET,
        CONTRACT,
        DEFAULT
    }
}