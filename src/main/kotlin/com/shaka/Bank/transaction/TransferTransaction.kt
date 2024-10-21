package com.shaka.Bank.transaction

import java.time.LocalDateTime

data class TransferTransaction (
    override val id: Long,
    override val sourceAccountId: Long,
    val destinationAccountId: Long,
    override val transactionAmount: Double,
    override val amountAfterTransaction: Double,
    override val timestamp: LocalDateTime,
): Transaction {
    override fun isAccountInvolvedInTransaction(accountId: Long): Boolean {
        return sourceAccountId == accountId || destinationAccountId == accountId
    }
}