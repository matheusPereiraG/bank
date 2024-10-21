package com.shaka.Bank.transaction.dto

import jakarta.validation.constraints.NotNull

data class DepositRequest (
    @field:NotNull
    val accountId: Long,
    @field:NotNull
    val amountToDeposit: Double,
)