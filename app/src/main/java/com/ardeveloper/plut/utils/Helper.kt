package com.ardeveloper.plut.utils

import java.text.DecimalFormat

object  Helper {

    fun convertToCurrency(value: String): String {
        val currentValue: Double
        currentValue = try {
            java.lang.Double.parseDouble(value)
        } catch (nfe: NumberFormatException) {
            0.0
        }

        return convertToCurrency(currentValue)
    }

    fun convertToCurrency(amount: Double): String {
        val formatter = DecimalFormat("#,###,###")
        return formatter.format(amount).replace(",", ".")
    }
}