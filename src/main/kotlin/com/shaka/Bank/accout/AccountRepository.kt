package com.shaka.Bank.accout

import com.shaka.Bank.core.GenericResult

interface AccountRepository {
    fun insertAccount(vararg accounts: Account)

    fun getNewAccountId(): Long

    fun getUserAccounts(userId: Long): GenericResult<List<Account>>
}