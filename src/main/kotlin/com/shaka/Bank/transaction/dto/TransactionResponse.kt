package com.shaka.Bank.transaction.dto

import java.math.BigDecimal

data class TransactionResponse (
    val newAccountAmount: BigDecimal
)