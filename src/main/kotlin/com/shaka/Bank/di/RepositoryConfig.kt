package com.shaka.Bank.di

import com.shaka.Bank.account.domain.AccountRepository
import com.shaka.Bank.account.AccountRepositoryImpl
import com.shaka.Bank.transaction.domain.TransactionRepository
import com.shaka.Bank.transaction.TransactionRepositoryImpl
import com.shaka.Bank.users.domain.UserRepository
import com.shaka.Bank.users.UserRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {

    @Bean
    fun usersRepository(): UserRepository {
        return UserRepositoryImpl()
    }

    @Bean
    fun accountRepository(): AccountRepository {
        return AccountRepositoryImpl()
    }

    @Bean
    fun transactionRepository(): TransactionRepository {
        return TransactionRepositoryImpl()
    }

}