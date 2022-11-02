package com.telkom.capex.ui.dashboard.helper.interfaces

import com.telkom.capex.ui.dashboard.helper.model.DashboardModel


interface DashboardInterface {
    fun doWithData(pojo: DashboardModel)
    fun setCurrentPageTo(position: Int)
}