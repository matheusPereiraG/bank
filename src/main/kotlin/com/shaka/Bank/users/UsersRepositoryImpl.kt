package com.shaka.Bank.users

import java.util.concurrent.atomic.AtomicLong

class UsersRepositoryImpl: UsersRepository {
    private val storage: HashMap<Long, User> = hashMapOf()

    private val userIdCreator: AtomicLong = AtomicLong(0)

    override fun insertUsers(vararg users: User) {
        for(user in users) {
            storage[user.id] = user
        }
    }

    override fun getNewUserId(): Long {
        return userIdCreator.getAndIncrement()
    }

}