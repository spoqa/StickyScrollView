package com.spoqa.sample.models

import java.math.BigDecimal

data class SampleData (
    val name: String,
    var standard: String,
    var count: Int,
    var unit: String,
    var amount: BigDecimal,
    var totalAmount: BigDecimal
)
