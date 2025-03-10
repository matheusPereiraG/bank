package com.shaka.Bank.account.dto

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CreateAccountRequest (
    @field:NotNull(message = "User id must be provided")
    val userId: Long,
    val initialAmount: BigDecimal?
)