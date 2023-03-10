package com.telkom.capex.menu.dashboard.helper.model

class DashboardRecentModel {
    lateinit var contractID: String
    lateinit var contractName: String

    constructor()
    constructor(contractID: String, contractName: String) {
        this.contractID = contractID
        this.contractName = contractName
    }
}