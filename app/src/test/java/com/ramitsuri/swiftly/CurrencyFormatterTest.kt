package com.ramitsuri.swiftly

import com.ramitsuri.swiftly.utils.CurrencyFormatter
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class CurrencyFormatterTest {

    @Test
    fun testFormat_shouldContainDollarSymbol_ifLocaleUS() {
        val formatter = CurrencyFormatter(Locale.US)
        Assert.assertTrue(formatter.format(BigDecimal("2.5")).contains("$"))
    }

    @Test
    fun testFormat_shouldContainPoundSymbol_ifLocaleUK() {
        val formatter = CurrencyFormatter(Locale.UK)
        Assert.assertTrue(formatter.format(BigDecimal("2.5")).contains("£"))
    }
}

