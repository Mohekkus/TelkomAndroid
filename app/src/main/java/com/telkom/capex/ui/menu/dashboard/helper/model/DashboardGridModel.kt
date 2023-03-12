package com.telkom.capex.ui.menu.dashboard.helper.model

class DashboardGridModel {
    private lateinit var sub: String
    private lateinit var onClick: String
    private lateinit var color: String
    private lateinit var title: String

    constructor()
    constructor(sub: String, onClick: String, color: String, title: String) {
        this.sub = sub
        this.title = title
        this.color = color
        this.onClick = onClick
    }

    fun getSub() = sub
    fun getTitle() = title
    fun getColor() = color
    fun getOnClick() = onClick
}