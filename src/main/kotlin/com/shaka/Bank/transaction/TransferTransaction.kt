package com.shaka.Bank.transaction

import java.math.BigDecimal
import java.time.LocalDateTime

data class TransferTransaction (
    override val id: Long,
    override val sourceAccountId: Long,
    val destinationAccountId: Long,
    override val transactionAmount: BigDecimal,
    override val amountAfterTransaction: BigDecimal,
    override val timestamp: LocalDateTime,
): Transaction {
    override fun isAccountInvolvedInTransaction(accountId: Long): Boolean {
        return sourceAccountId == accountId || destinationAccountId == accountId
    }
}