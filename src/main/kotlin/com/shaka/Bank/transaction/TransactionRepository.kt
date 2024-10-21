package com.shaka.Bank.transaction

interface TransactionRepository {
    fun insertTransactions(vararg transactions: Transaction)

    fun getNewTransactionId(): Long

    fun getTransactionsForAccount(accountId: Long): List<Transaction>
}