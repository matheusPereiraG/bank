package com.shaka.Bank.transaction.dto

import java.math.BigDecimal

data class TransactionHistoryResponse(
    val transactions: List<TransactionItemResponse>
)


data class TransactionItemResponse(
    val transactionType: String,
    val timestamp: String,
    val transactionAmount: BigDecimal,
)