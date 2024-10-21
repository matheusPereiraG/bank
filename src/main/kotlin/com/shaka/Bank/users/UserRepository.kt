package com.shaka.Bank.users

import com.shaka.Bank.core.GenericResult

interface UserRepository {
    fun insertUsers(vararg users: User)

    fun getUserById(userId: Long): User?

    fun getNewUserId(): Long

    fun deactivateUser(id: Long): GenericResult<String>
}