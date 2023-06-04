package com.telkom.capex.etc

import com.telkom.capex.ui.menu.dashboard.helper.MyJavascriptInterface
import javax.inject.Inject

class Utility @Inject constructor() {
    @Inject lateinit var timelyGreet: TimelyGreet
    @Inject lateinit var animUtils: AnimUtils
    @Inject lateinit var money: Money
}