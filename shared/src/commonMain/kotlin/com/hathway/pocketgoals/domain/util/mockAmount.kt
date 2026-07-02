package com.hathway.pocketgoals.domain.util


object mockAmount {

    fun formatMockAmount(amount: Double): String {
        // Simple manual formatting to match the Indian system style in the image for the mock
        val longVal = amount.toLong()
        return when {
            longVal >= 100000 -> {
                val lakhs = longVal / 100000
                val thousands = (longVal % 100000) / 1000
                val hundreds = longVal % 1000
                if (lakhs > 0) {
                    if (thousands > 0) {
                        "$lakhs,${thousands.toString().padStart(2, '0')},${
                            hundreds.toString().padStart(3, '0')
                        }"
                    } else {
                        "$lakhs,00,${hundreds.toString().padStart(3, '0')}"
                    }
                } else {
                    "$longVal"
                }
            }

            longVal >= 1000 -> {
                val thousands = longVal / 1000
                val hundreds = longVal % 1000
                "$thousands,${hundreds.toString().padStart(3, '0')}"
            }

            else -> longVal.toString()
        }
    }
}
