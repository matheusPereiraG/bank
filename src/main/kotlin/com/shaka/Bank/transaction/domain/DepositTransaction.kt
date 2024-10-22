package com.shaka.Bank.transaction.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class DepositTransaction(
    override val id: Long,
    override val sourceAccountId: Long,
    override val transactionAmount: BigDecimal,
    override val amountAfterTransaction: BigDecimal,
    override val timestamp: LocalDateTime,
): Transaction {
    override fun isAccountInvolvedInTransaction(accountId: Long): Boolean {
        return sourceAccountId == accountId
    }
}