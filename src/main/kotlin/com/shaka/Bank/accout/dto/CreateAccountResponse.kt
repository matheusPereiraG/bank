package com.shaka.Bank.accout.dto

import java.math.BigDecimal

data class CreateAccountResponse(
    val accountId: Long,
    val currentAmount: BigDecimal,
)