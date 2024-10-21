package com.shaka.Bank

import com.shaka.Bank.account.AccountController
import com.shaka.Bank.account.AccountRepository
import com.shaka.Bank.account.dto.CreateAccountRequest
import com.shaka.Bank.core.GenericResult
import com.shaka.Bank.users.User
import com.shaka.Bank.users.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import kotlin.test.Test

@SpringBootTest
class AccountControllerTest {
    @Autowired
    lateinit var accountController: AccountController

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `should only create account if user exists`() {
        userRepository.insertUsers(User(id = 1, firstName = "Cristiano", lastName = "Ronaldo", isActive = true))
        val response = accountController.newAccount(CreateAccountRequest(userId = 1, initialAmount = null))

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body?.data?.accountId).isNotNull()
    }

    @Test
    fun `initial amount should default to 0`() {
        userRepository.insertUsers(User(id = 1, firstName = "Cristiano", lastName = "Ronaldo", isActive = true))
        val response = accountController.newAccount(CreateAccountRequest(userId = 1, initialAmount = null))

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body?.data?.currentAmount).isEqualTo(BigDecimal(0))
    }

    @Test
    fun `initial amount should be populated when provided`() {
        userRepository.insertUsers(User(id = 1, firstName = "Cristiano", lastName = "Ronaldo", isActive = true))
        val response = accountController.newAccount(CreateAccountRequest(userId = 1, initialAmount = BigDecimal(123.99)))

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body?.data?.currentAmount).isEqualTo(BigDecimal(123.99))

        val createdAccount = accountRepository.getAccountById(response.body?.data?.accountId!!)
        assertThat(createdAccount).isInstanceOf(GenericResult.Success::class.java)
        assertThat((createdAccount as GenericResult.Success).data?.amount).isEqualTo(BigDecimal(123.99))
    }
}