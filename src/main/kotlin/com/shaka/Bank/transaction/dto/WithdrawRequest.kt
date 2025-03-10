package com.shaka.Bank.transaction.dto

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class WithdrawRequest (
    @field:NotNull
    val accountId: Long,
    @field:NotNull
    val amountToWithdraw: BigDecimal,
)