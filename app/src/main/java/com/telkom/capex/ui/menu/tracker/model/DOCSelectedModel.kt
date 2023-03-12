package com.telkom.capex.ui.menu.tracker.model

import com.telkom.capex.ui.menu.tracker.fragments.doc.model.ResultDOC

class DOCSelectedModel {

    private var selected: Boolean = false
    private lateinit var data: ResultDOC

    constructor()
    constructor(resultDOC: ResultDOC) {
        data = resultDOC
    }

    fun getTitle() = data.contractName

    fun getProgress() = data.contractProgress

    fun itemSelected() = selected
    fun setItemSelected(boolean: Boolean) {
        selected = boolean
    }


}