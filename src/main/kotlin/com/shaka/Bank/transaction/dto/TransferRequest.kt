package com.shaka.Bank.transaction.dto

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class TransferRequest (
    @field:NotNull
    val sourceAccountId: Long,
    @field:NotNull
    val destinationAccountId: Long,
    @field:NotNull
    val amount: BigDecimal,
)