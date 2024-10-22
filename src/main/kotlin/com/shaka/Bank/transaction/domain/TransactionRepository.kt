package com.shaka.Bank.transaction.domain

interface TransactionRepository {
    fun insertTransactions(vararg transactions: Transaction)

    fun getNewTransactionId(): Long

    fun getTransactionsForAccount(accountId: Long): List<Transaction>
}