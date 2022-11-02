package com.telkom.capex.ui.tracker.model

class DOCSelectedModel {

    private var selected: Boolean = false
    private lateinit var title: String
    private var progress: Int = 0

    constructor()
    constructor(title: String, progress: Int) {
        this.title = title
        this. progress = progress
    }

    fun getTitle() = title

    fun getProgress() = progress

    fun itemSelected() = selected
    fun setItemSelected(boolean: Boolean) {
        selected = boolean
    }


}