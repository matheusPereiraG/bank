package com.shaka.Bank.transaction

import com.shaka.Bank.accout.AccountRepository
import com.shaka.Bank.core.GenericResult
import com.shaka.Bank.core.dto.ApiResponse
import com.shaka.Bank.transaction.dto.*
import com.shaka.Bank.transaction.utils.toResponseItems
import com.shaka.Bank.users.UserRepository
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@Validated
class TransactionController(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
) {

    @GetMapping("/transaction/history/{id}")
    fun getAccountTransactionHistory(@PathVariable(name = "id") accountId: Long): ResponseEntity<ApiResponse<TransactionHistoryResponse>> {
        val result = accountRepository.getAccountById(accountId)

        if (result is GenericResult.Error) {
            return ResponseEntity.badRequest().body(ApiResponse(message = result.errorMsg))
        }

        val account = (result as GenericResult.Success).data ?: return ResponseEntity.badRequest()
            .body(ApiResponse(message = "Account does not exist"))

        val user = userRepository.getUserById(account.userId)

        if (user == null || !user.isActive) {
            return ResponseEntity.badRequest()
                .body(ApiResponse(message = "User does not exist for this account or is deactivated"))
        }

        val accountTransactions = transactionRepository.getTransactionsForAccount(accountId)


        if (accountTransactions.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val transactionItems = accountTransactions.toResponseItems(accountId)

        return ResponseEntity.ok(ApiResponse(TransactionHistoryResponse(transactions = transactionItems)))
    }

    @PostMapping("/transaction/transfer")
    fun transferToAccount(@Valid @RequestBody request: TransferRequest): ResponseEntity<ApiResponse<TransactionResponse>> {
        if (request.sourceAccountId == request.destinationAccountId) {
            return ResponseEntity.badRequest().body(ApiResponse(message = "Can't transfer to the same account"))
        }

        val sourceAccountResult = accountRepository.getAccountById(request.sourceAccountId)
        val destinationAccountResult = accountRepository.getAccountById(request.destinationAccountId)

        if (sourceAccountResult is GenericResult.Error) {
            return ResponseEntity.badRequest().body(ApiResponse(message = sourceAccountResult.errorMsg))
        }

        if (destinationAccountResult is GenericResult.Error) {
            return ResponseEntity.badRequest().body(ApiResponse(message = destinationAccountResult.errorMsg))
        }

        val sourceAccount = (sourceAccountResult as GenericResult.Success).data!!
        val destinationAccount = (destinationAccountResult as GenericResult.Success).data!!

        val sourceUser = userRepository.getUserById(sourceAccount.userId)
        val destinationUser = userRepository.getUserById(destinationAccount.userId)

        if (sourceUser == null || !sourceUser.isActive) {
            return ResponseEntity.badRequest()
                .body(ApiResponse(message = "Source user for transfer does not exist or is deactivated"))
        }

        if (destinationUser == null || !destinationUser.isActive) {
            return ResponseEntity.badRequest()
                .body(ApiResponse(message = "Destination user for transfer does not exist or is deactivated"))
        }

        val newSourceAccount = sourceAccount.copy(
            amount = sourceAccount.amount - request.amount
        )
        val newDestinationAccount = destinationAccount.copy(
            amount = destinationAccount.amount + request.amount
        )
        val newTransaction = TransferTransaction(
            id = transactionRepository.getNewTransactionId(),
            sourceAccountId = sourceAccount.id,
            destinationAccountId = destinationAccount.id,
            transactionAmount = request.amount,
            amountAfterTransaction = newSourceAccount.amount,
            timestamp = LocalDateTime.now(),
        )

        accountRepository.insertAccount(newSourceAccount, newDestinationAccount)
        transactionRepository.insertTransactions(newTransaction)
        return ResponseEntity.ok(ApiResponse(data = TransactionResponse(newAccountAmount = newSourceAccount.amount)))
    }

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

        return ResponseEntity.ok()
            .body(ApiResponse(data = TransactionResponse(newAccountAmount = accountUpdated.amount)))
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

        return ResponseEntity.ok()
            .body(ApiResponse(data = TransactionResponse(newAccountAmount = accountUpdated.amount)))
    }
}