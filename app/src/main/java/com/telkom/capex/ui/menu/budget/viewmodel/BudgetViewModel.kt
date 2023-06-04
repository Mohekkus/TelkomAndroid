package com.telkom.capex.ui.menu.budget.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telkom.capex.etc.MonthModifier
import com.telkom.capex.network.utility.ServiceHandler
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResponse
import com.telkom.capex.ui.menu.budget.helper.model.BudgetListResultItem
import com.telkom.capex.ui.menu.budget.helper.repo.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Year
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(): ViewModel() {

}