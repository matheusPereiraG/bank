package com.shaka.Bank.users

import com.shaka.Bank.core.GenericResult
import com.shaka.Bank.core.dto.ApiResponse
import com.shaka.Bank.users.dto.UserCreationRequest
import com.shaka.Bank.users.dto.UserCreationResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
class UserController(private val usersRepository: UsersRepository) {
    @PostMapping("/user")
    fun newUser(
        @Valid @RequestBody request: UserCreationRequest,
    ): ResponseEntity<ApiResponse<UserCreationResponse>> {
        val newId = usersRepository.getNewUserId()
        val newUser = User(
            id = newId,
            firstName = request.firstName,
            lastName = request.lastName,
            isActive = true,
        )
        usersRepository.insertUsers(newUser)
        return ResponseEntity.ok().body(
            ApiResponse(data = UserCreationResponse(userId = newUser.id), message = "User created successfully")
        )
    }

    @PatchMapping("/user/deactivate/{id}")
    fun deactivateUser(@PathVariable id: Long): ResponseEntity<ApiResponse<Nothing>> {
        val result = usersRepository.deactivateUser(id)
        return when(result) {
            is GenericResult.Success -> {
                ResponseEntity.ok().body(ApiResponse(message = result.data))
            }

            is GenericResult.Error -> {
                ResponseEntity.badRequest().body(ApiResponse(message = result.errorMsg))
            }
        }
    }
}