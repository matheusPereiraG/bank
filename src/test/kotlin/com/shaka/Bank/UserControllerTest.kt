package com.shaka.Bank

import com.shaka.Bank.users.UserController
import com.shaka.Bank.users.UserRepository
import com.shaka.Bank.users.dto.UserCreationRequest
import jakarta.validation.ConstraintViolationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import kotlin.test.Test


@SpringBootTest
class UserControllerTest {
    @Autowired
    lateinit var userController: UserController

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        assertThat(userController).isNotNull()
    }

    @Test
    fun `should add user with required params`() {
        val response = userController.newUser(UserCreationRequest(firstName = "Matheus", lastName = "Gonçalves"))
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val createdUserId = response.body?.data?.userId
        assertThat(createdUserId).isNotNull()

        val user = userRepository.getUserById(createdUserId!!)

        assertThat(user).isNotNull()
    }

    @Test
    fun `should fail if first name is not provided`() {
        assertThrows<ConstraintViolationException> {
            userController.newUser(UserCreationRequest(firstName = "", lastName = "Gonçalves"))
        }
    }

    @Test
    fun `should fail if last name is not provided`() {
        assertThrows<ConstraintViolationException> {
            userController.newUser(UserCreationRequest(firstName = "Matheus", lastName = ""))
        }
    }

    @Test
    fun `user creation isActive flag defaults to true`() {
        val response = userController.newUser(UserCreationRequest(firstName = "Matheus", lastName = "Gonçalves"))
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val createdUserId = response.body?.data?.userId
        assertThat(createdUserId).isNotNull()

        val user = userRepository.getUserById(createdUserId!!)

        assertThat(user?.isActive).isTrue()
    }

    @Test
    fun `should deactivate user`() {
        val response = userController.newUser(UserCreationRequest(firstName = "Matheus", lastName = "Gonçalves"))
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val createdUserId = response.body?.data?.userId
        assertThat(createdUserId).isNotNull()

        val user = userRepository.getUserById(createdUserId!!)

        assertThat(user?.isActive).isTrue()

        val deactivationResponse = userController.deactivateUser(createdUserId)
        assertThat(deactivationResponse.statusCode).isEqualTo(HttpStatus.OK)

        val userAfterDeactivation = userRepository.getUserById(createdUserId)
        assertThat(userAfterDeactivation?.isActive).isFalse()
    }
}