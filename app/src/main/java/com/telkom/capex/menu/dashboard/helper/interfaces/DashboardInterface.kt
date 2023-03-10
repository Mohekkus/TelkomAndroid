package com.telkom.capex.menu.dashboard.helper.interfaces

import com.telkom.capex.menu.dashboard.helper.model.DashboardModel


interface DashboardInterface {
    fun doWithData(pojo: DashboardModel)
    fun setCurrentPageTo(position: Int)
}