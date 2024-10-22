package com.shaka.Bank.account

import com.shaka.Bank.account.domain.Account
import com.shaka.Bank.account.domain.AccountRepository
import com.shaka.Bank.core.GenericResult
import java.util.concurrent.atomic.AtomicLong

class AccountRepositoryImpl : AccountRepository {
    private val storage: HashMap<Long, Account> = hashMapOf()
    private val id: AtomicLong = AtomicLong(0)
    override fun insertAccount(vararg accounts: Account) {
        for (account in accounts) {
            storage[account.id] = account
        }
    }

    override fun getAccountById(accountId: Long): GenericResult<Account> {
        val account = storage[accountId]
        return if(account != null) {
            GenericResult.Success(data = account)
        } else {
            GenericResult.Error(errorMsg = "Account with id: $accountId does not exist")
        }
    }

    override fun getNewAccountId(): Long {
        return id.getAndIncrement()
    }

    override fun getUserAccounts(userId: Long): GenericResult<List<Account>> {
        val userAccounts = mutableListOf<Account>()
        for (entry in storage.entries) {
            if (entry.value.userId == userId) {
                userAccounts.add(entry.value)
            }
        }
        if (userAccounts.isNotEmpty()) return GenericResult.Success(data = userAccounts)
        return GenericResult.Error(errorMsg = "No account found for the provided user")
    }
}