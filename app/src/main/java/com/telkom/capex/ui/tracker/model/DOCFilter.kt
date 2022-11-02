package com.telkom.capex.ui.tracker.model

class DOCFilter {

    private lateinit var status: String
    private var isSelected: Boolean = false

    constructor()
    constructor(status: String) {
        this.status = status
    }

    fun getStatus() = status
    fun getIsSelected() = isSelected
    fun setIsSelected(boolean: Boolean) {
        isSelected = boolean
    }

}