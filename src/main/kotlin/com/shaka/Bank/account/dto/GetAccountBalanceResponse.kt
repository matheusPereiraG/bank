package com.shaka.Bank.account.dto

import java.math.BigDecimal

data class GetAccountBalanceResponse (
    val amount: BigDecimal,
)