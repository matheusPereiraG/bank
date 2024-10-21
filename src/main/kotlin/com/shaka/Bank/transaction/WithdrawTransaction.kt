package com.shaka.Bank.transaction

import java.math.BigDecimal
import java.time.LocalDateTime

data class WithdrawTransaction(
    override val id: Long,
    override val sourceAccountId: Long,
    override val timestamp: LocalDateTime,
    override val transactionAmount: BigDecimal,
    override val amountAfterTransaction: BigDecimal,
) : Transaction {
    override fun isAccountInvolvedInTransaction(accountId: Long): Boolean {
        return sourceAccountId == accountId
    }
}