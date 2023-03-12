package com.telkom.capex.etc

import javax.inject.Inject

class Money @Inject constructor() {

    fun format(amount: Long): String {
        val amountString = String.format("%,d", amount)
        return "IDR $amountString"
    }

    fun percentage(total: Long, amount: Long): String {
        val amountString = (total/amount) * 100
        return "$amountString%"
    }
}