package com.shaka.Bank.transaction

import java.time.LocalDateTime

data class WithdrawTransaction(
    override val id: Long,
    override val sourceAccountId: Long,
    override val timestamp: LocalDateTime,
    override val transactionAmount: Double,
    override val amountAfterTransaction: Double,
) : Transaction {
    override fun isAccountInvolvedInTransaction(accountId: Long): Boolean {
        return sourceAccountId == accountId
    }
}