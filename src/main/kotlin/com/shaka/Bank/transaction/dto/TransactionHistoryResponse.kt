package com.shaka.Bank.transaction.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

data class TransactionHistoryResponse(
    val transactions: List<TransactionItemResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TransactionItemResponse(
    val transactionType: String,
    val timestamp: String,
    val transactionAmount: BigDecimal,
    val fromAccount: Long? = null,
    val toAccount: Long? = null,
)

