package com.shaka.Bank.account.dto

import java.math.BigDecimal

data class CreateAccountResponse(
    val accountId: Long,
    val currentAmount: BigDecimal,
)