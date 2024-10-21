package com.shaka.Bank.transaction

import com.shaka.Bank.accout.AccountRepository
import com.shaka.Bank.core.GenericResult
import com.shaka.Bank.core.dto.ApiResponse
import com.shaka.Bank.transaction.dto.DepositRequest
import com.shaka.Bank.transaction.dto.TransactionResponse
import com.shaka.Bank.transaction.dto.WithdrawRequest
import com.shaka.Bank.users.UserRepository
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@Validated
class TransactionController(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
) {

    @PostMapping("/transaction/withdraw")
    fun withdrawFromAccount(@Valid @RequestBody request: WithdrawRequest): ResponseEntity<ApiResponse<TransactionResponse>> {
        val result = accountRepository.getAccountById(request.accountId)

        if (result is GenericResult.Error) {
            return ResponseEntity.badRequest().body(ApiResponse(message = result.errorMsg))
        }

        val account = (result as GenericResult.Success).data!!

        val user = userRepository.getUserById(account.userId)

        if (user == null || !user.isActive) {
            return ResponseEntity.badRequest()
                .body(ApiResponse(message = "User does not exist for this account or is deactivated"))
        }

        val accountUpdated = account.copy(
            amount = account.amount - request.amountToWithdraw
        )

        val withDrawTransaction = WithdrawTransaction(
            id = transactionRepository.getNewTransactionId(),
            sourceAccountId = account.id,
            timestamp = LocalDateTime.now(),
            transactionAmount = request.amountToWithdraw,
            amountAfterTransaction = accountUpdated.amount
        )

        accountRepository.insertAccount(accountUpdated)
        transactionRepository.insertTransactions(withDrawTransaction)

        return ResponseEntity.ok().body(ApiResponse(data = TransactionResponse(newAccountAmount = accountUpdated.amount)))
    }

    @PostMapping("/transaction/deposit")
    fun depositToAccount(@Valid @RequestBody request: DepositRequest): ResponseEntity<ApiResponse<TransactionResponse>> {
        val result = accountRepository.getAccountById(request.accountId)

        if (result is GenericResult.Error) {
            return ResponseEntity.badRequest().body(ApiResponse(message = result.errorMsg))
        }

        val account = (result as GenericResult.Success).data!!

        val user = userRepository.getUserById(account.userId)

        if (user == null || !user.isActive) {
            return ResponseEntity.badRequest()
                .body(ApiResponse(message = "User does not exist for this account or is deactivated"))
        }

        val accountUpdated = account.copy(
            amount = account.amount + request.amountToDeposit
        )

        val depositTransaction = DepositTransaction(
            id = transactionRepository.getNewTransactionId(),
            sourceAccountId = account.id,
            timestamp = LocalDateTime.now(),
            transactionAmount = request.amountToDeposit,
            amountAfterTransaction = accountUpdated.amount
        )

        accountRepository.insertAccount(accountUpdated)
        transactionRepository.insertTransactions(depositTransaction)

        return ResponseEntity.ok().body(ApiResponse(data = TransactionResponse(newAccountAmount = accountUpdated.amount)))
    }


}