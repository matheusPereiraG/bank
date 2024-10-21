package com.shaka.Bank.transaction

import java.time.LocalDateTime

interface Transaction {
    val id: Long
    val sourceAccountId: Long
    val transactionAmount: Double
    val amountAfterTransaction: Double
    val timestamp: LocalDateTime

    fun isAccountInvolvedInTransaction(accountId: Long): Boolean
}