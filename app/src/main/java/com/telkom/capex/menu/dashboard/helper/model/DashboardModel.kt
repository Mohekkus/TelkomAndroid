package com.telkom.capex.menu.dashboard.helper.model


class DashboardModel {

    lateinit var bastTotal: String
    lateinit var recentActivities: ArrayList<DashboardRecentModel>
    lateinit var gridMenu: ArrayList<DashboardGridModel> /* = java.util.ArrayList<me.mohekkus.maddashboard.activity.dashboard.helper.model.DashboardGrid> */

    constructor()
    constructor(bastTotal: String, gridMenu: ArrayList<DashboardGridModel>, recentActivities: ArrayList<DashboardRecentModel>) {
        this.bastTotal = bastTotal
        this.recentActivities = recentActivities
        this.gridMenu = gridMenu
    }
}