package com.telkom.capex.network

object ModuleInfo {
    const val address = "103.181.183.149"
    const val port = "5000"

    fun getAddress() = "http://$address:$port"
}