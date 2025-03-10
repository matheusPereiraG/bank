package com.shaka.Bank.account.domain

import com.shaka.Bank.core.GenericResult

interface AccountRepository {
    fun insertAccount(vararg accounts: Account)

    fun getAccountById(accountId: Long): GenericResult<Account>

    fun getNewAccountId(): Long

    fun getUserAccounts(userId: Long): GenericResult<List<Account>>
}