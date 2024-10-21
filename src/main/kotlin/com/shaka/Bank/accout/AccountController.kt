package com.shaka.Bank.accout

import com.shaka.Bank.accout.dto.CreateAccountRequest
import com.shaka.Bank.accout.dto.CreateAccountResponse
import com.shaka.Bank.accout.dto.GetAccountBalanceResponse
import com.shaka.Bank.core.GenericResult
import com.shaka.Bank.core.dto.ApiResponse
import com.shaka.Bank.users.UserRepository
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@Validated
class AccountController(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) {
    @PostMapping("/account")
    fun newAccount(@Valid @RequestBody request: CreateAccountRequest): ResponseEntity<ApiResponse<CreateAccountResponse>> {
        val userId = request.userId

        val user = userRepository.getUserById(userId)
            ?: return ResponseEntity.badRequest()
                .body(ApiResponse(message = "User does not exist, please create one before creating an account"))

        if (!user.isActive) return ResponseEntity.badRequest()
            .body(ApiResponse(message = "User is deactivated, cannot proceed with the operation"))

        val newAccount = Account(
            id = accountRepository.getNewAccountId(),
            userId = user.id,
            amount = request.initialAmount ?: BigDecimal(0)
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

    @GetMapping("/account/balance/{id}")
    fun getBalance(@PathVariable id: Long): ResponseEntity<ApiResponse<GetAccountBalanceResponse>> {
        val result = accountRepository.getAccountById(id)

        return when (result) {
            is GenericResult.Success -> {
                val user = userRepository.getUserById(result.data!!.userId)
                if(user?.isActive == true) {
                    ResponseEntity.ok(ApiResponse(data = GetAccountBalanceResponse(amount = result.data.amount)))
                } else {
                    ResponseEntity.badRequest().body(ApiResponse(message = "User is not available"))
                }
            }

            is GenericResult.Error -> {
                ResponseEntity.badRequest().body(ApiResponse(message = result.errorMsg))
            }
        }
    }
}