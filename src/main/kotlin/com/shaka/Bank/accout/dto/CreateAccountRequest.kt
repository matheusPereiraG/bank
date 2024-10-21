package com.shaka.Bank.accout.dto

import jakarta.validation.constraints.NotNull

data class CreateAccountRequest (
    @field:NotNull(message = "User id must be provided")
    val userId: Long,
    val initialAmount: Double?
)