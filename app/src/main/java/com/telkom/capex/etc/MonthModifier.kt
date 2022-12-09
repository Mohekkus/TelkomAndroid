package com.telkom.capex.etc

import java.text.SimpleDateFormat
import java.util.*

object MonthModifier {

    fun currentMonthInt(): Int {
        val dateFormat = SimpleDateFormat("MM", Locale.getDefault())
        return dateFormat.format(Date()).toInt()
    }

    fun fromMonth(month: String) : Int = when (month) {
        "January" -> 1
        "February" -> 2
        "March" -> 3
        "April" -> 4
        "Mei" -> 5
        "June"-> 6
        "July"-> 7
        "August"-> 8
        "September"-> 9
        "October"-> 10
        "November"-> 11
        "December"-> 12
        else -> 13
    }

    fun getMonth(num: Int): String = when (num) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "Mei"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> "Invalid month is it doomsday already? Fuck!"
    }

    fun getMonthPrefix(num: Int): String = when (num) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "Mei"
        6 -> "Jun"
        7 -> "July"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> "Err, Null"
    }
}