package com.shaka.Bank.transaction

import java.math.BigDecimal
import java.time.LocalDateTime

interface Transaction {
    val id: Long
    val sourceAccountId: Long
    val transactionAmount: BigDecimal
    val amountAfterTransaction: BigDecimal
    val timestamp: LocalDateTime

    fun isAccountInvolvedInTransaction(accountId: Long): Boolean
}