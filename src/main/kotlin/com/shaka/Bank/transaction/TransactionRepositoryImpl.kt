package com.shaka.Bank.transaction

import com.shaka.Bank.transaction.domain.Transaction
import com.shaka.Bank.transaction.domain.TransactionRepository
import java.util.concurrent.atomic.AtomicLong

class TransactionRepositoryImpl : TransactionRepository {
    private val storage: HashMap<Long, Transaction> = hashMapOf()
    private val id: AtomicLong = AtomicLong(0)
    override fun insertTransactions(vararg transactions: Transaction) {
        for (transaction in transactions) {
            storage[transaction.id] = transaction
        }
    }

    override fun getNewTransactionId(): Long {
        return id.getAndIncrement()
    }

    override fun getTransactionsForAccount(accountId: Long): List<Transaction> {
        val accountTransactions = mutableListOf<Transaction>()
        for (entry in storage.entries) {
            if (entry.value.isAccountInvolvedInTransaction(accountId)) {
                accountTransactions.add(entry.value)
            }
        }
        return accountTransactions.sortedByDescending { it.timestamp }
    }
}