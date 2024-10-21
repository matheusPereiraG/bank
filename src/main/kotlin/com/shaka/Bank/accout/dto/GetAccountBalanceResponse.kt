package com.shaka.Bank.accout.dto

import java.math.BigDecimal

data class GetAccountBalanceResponse (
    val amount: BigDecimal,
)