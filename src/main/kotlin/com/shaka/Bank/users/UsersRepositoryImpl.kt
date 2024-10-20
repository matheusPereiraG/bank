package com.shaka.Bank.users

class UsersRepositoryImpl: UsersRepository {
    private val storage: HashMap<Long, User> = hashMapOf()

    override fun insertUsers(vararg users: User) {

    }

}