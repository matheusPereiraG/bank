package com.shaka.Bank.accout.dto

data class CreateAccountResponse(
    val accountId: Long,
    val currentAmount: Double,
)