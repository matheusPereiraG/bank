package com.shaka.Bank.accout

import com.shaka.Bank.accout.dto.CreateAccountRequest
import com.shaka.Bank.accout.dto.CreateAccountResponse
import com.shaka.Bank.core.dto.ApiResponse
import com.shaka.Bank.users.UsersRepository
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class AccountController(
    private val accountRepository: AccountRepository,
    private val usersRepository: UsersRepository
) {
    @PostMapping("/account")
    fun newAccount(@Valid @RequestBody request: CreateAccountRequest): ResponseEntity<ApiResponse<CreateAccountResponse>> {
        val userId = request.userId

        val user = usersRepository.getUserById(userId)
            ?: return ResponseEntity.badRequest()
                .body(ApiResponse(message = "User does not exist, please create one before creating an account"))

        if (!user.isActive) return ResponseEntity.badRequest()
            .body(ApiResponse(message = "User is deactivated, cannot proceed with the operation"))

        val newAccount = Account(
            id = accountRepository.getNewAccountId(),
            userId = user.id,
            amount = request.initialAmount ?: 0.0
        )

        accountRepository.insertAccount(newAccount)
        return ResponseEntity.ok().body(
            ApiResponse(
                data = CreateAccountResponse(
                    newAccount.id,
                    currentAmount = newAccount.amount
                )
            )
        )
    }


}