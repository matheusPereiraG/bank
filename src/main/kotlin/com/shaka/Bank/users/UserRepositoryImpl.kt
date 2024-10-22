package com.shaka.Bank.users

import com.shaka.Bank.core.GenericResult
import com.shaka.Bank.users.domain.User
import com.shaka.Bank.users.domain.UserRepository
import java.util.concurrent.atomic.AtomicLong

class UserRepositoryImpl : UserRepository {
    private val storage: HashMap<Long, User> = hashMapOf()

    private val userIdCreator: AtomicLong = AtomicLong(0)

    override fun insertUsers(vararg users: User) {
        for (user in users) {
            storage[user.id] = user
        }
    }

    override fun getUserById(userId: Long): User? {
        return try {
            storage[userId]
        } catch (e: Exception) {
            null
        }
    }

    override fun getNewUserId(): Long {
        return userIdCreator.getAndIncrement()
    }

    override fun deactivateUser(id: Long): GenericResult<String> {
        return try {
            storage[id] = storage[id]!!.copy(isActive = false)
            GenericResult.Success(data = "User with id $id deactivated successfully")
        } catch (e: Exception) {
            GenericResult.Error(errorMsg = "User does not exist for this Id")
        }
    }
}