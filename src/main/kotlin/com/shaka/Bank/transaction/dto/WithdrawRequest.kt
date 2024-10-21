package com.shaka.Bank.transaction.dto

import jakarta.validation.constraints.NotNull

data class WithdrawRequest (
    @field:NotNull
    val accountId: Long,
    @field:NotNull
    val amountToWithdraw: Double,
)