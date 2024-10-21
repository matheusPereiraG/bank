package com.shaka.Bank.accout

import java.math.BigDecimal

data class Account (
    val id: Long,
    val userId: Long,
    val amount: BigDecimal,
)