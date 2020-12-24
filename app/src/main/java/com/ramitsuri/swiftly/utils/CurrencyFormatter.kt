package com.ramitsuri.swiftly.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class CurrencyFormatter(locale: Locale = Locale.US) {
    private var formatter: NumberFormat = NumberFormat.getCurrencyInstance(locale)

    fun format(value: BigDecimal): String {
        return formatter.format(value)
    }
}