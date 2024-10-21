package com.shaka.Bank.users

interface UsersRepository {
    fun insertUsers(vararg users: User)

    fun getNewUserId(): Long
}