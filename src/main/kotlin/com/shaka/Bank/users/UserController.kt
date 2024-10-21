package com.shaka.Bank.users

import com.shaka.Bank.users.dto.UserCreationRequest
import com.shaka.Bank.users.dto.UserCreationResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
class UserController(private val usersRepository: UsersRepository) {
    @PostMapping("/user")
    fun newUser(
        @Valid @RequestBody request: UserCreationRequest,
    ): ResponseEntity<UserCreationResponse> {
        val newId = usersRepository.getNewUserId()
        val newUser = User(
            id = newId,
            firstName = request.firstName,
            lastName = request.lastName,
            isActive = true,
        )
        usersRepository.insertUsers(newUser)
        return ResponseEntity.ok().body(UserCreationResponse(userId = newUser.id))
    }
}