package com.shaka.Bank.transaction.dto

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class DepositRequest (
    @field:NotNull
    val accountId: Long,
    @field:NotNull
    val amountToDeposit: BigDecimal,
)