package com.shaka.Bank.di

import com.shaka.Bank.accout.AccountRepository
import com.shaka.Bank.accout.AccountRepositoryImpl
import com.shaka.Bank.users.UsersRepository
import com.shaka.Bank.users.UsersRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {

    @Bean
    fun usersRepository(): UsersRepository {
        return UsersRepositoryImpl()
    }

    @Bean
    fun accountRepository(): AccountRepository {
        return AccountRepositoryImpl()
    }

}