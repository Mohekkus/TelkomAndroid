package com.telkom.capex.ui.tracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.telkom.capex.ui.tracker.model.DOCSelectedModel

class TrackerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private val _selected = MutableLiveData<Int>().apply {
        value = 0
    }
    val selected: LiveData<Int> = _selected
    fun setSelected(number: Int) {
        _selected.value = number
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
}