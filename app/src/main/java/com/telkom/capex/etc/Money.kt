package com.telkom.capex.etc

import javax.inject.Inject
import kotlin.math.roundToInt

class Money @Inject constructor() {

    fun formatNoIDR(amount: Long): String = String.format("%,d", amount)
    fun format(amount: Long): String {
        val amountString = String.format("%,d", amount)
        return "IDR $amountString"
    }
    fun formatToMil(amount: Long): String {
        val amountString = String.format("%,d", amount)
        val formatted = amountString.substring(0,5)
        val tag =
            when {
                amount >= 1_000_000_000_000L -> " T"
                amount >= 1_000_000_000L -> " B"
                amount >= 1_000_000L -> " M"
                amount >= 1_000L -> " K"
                else -> ""
            }
        return "$formatted$tag"
    }
    fun percentage(total: Long, amount: Long): Int {
        val calculatedAmount = amount.toFloat() / total.toFloat() * 100
        return calculatedAmount.toDouble().roundToInt()
    }
}