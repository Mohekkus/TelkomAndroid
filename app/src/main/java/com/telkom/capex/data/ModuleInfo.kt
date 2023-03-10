package com.telkom.capex.data

object ModuleInfo {
    const val address = "103.181.183.149"
    const val port = "5000"

    fun getAddress() = "http://${ModuleInfo.address}:${ModuleInfo.port}"
}