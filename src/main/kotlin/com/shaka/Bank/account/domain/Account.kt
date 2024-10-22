package com.shaka.Bank.account.domain

import java.math.BigDecimal

data class Account (
    val id: Long,
    val userId: Long,
    val amount: BigDecimal,
)