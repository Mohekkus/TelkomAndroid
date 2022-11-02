package com.telkom.capex.etc

import com.telkom.capex.etc.AnimUtils
import com.telkom.capex.etc.TimelyGreet
import javax.inject.Inject

class Utility @Inject constructor() {
    @Inject lateinit var timelyGreet: TimelyGreet
    @Inject lateinit var animUtils: AnimUtils
}