package com.shaka.Bank.transaction.utils

import com.shaka.Bank.transaction.DepositTransaction
import com.shaka.Bank.transaction.Transaction
import com.shaka.Bank.transaction.TransferTransaction
import com.shaka.Bank.transaction.WithdrawTransaction
import com.shaka.Bank.transaction.dto.TransactionItemResponse


fun List<Transaction>.toResponseItems(accountId: Long): List<TransactionItemResponse> {
    return this.mapNotNull {
        when (it) {
            is WithdrawTransaction -> {
                TransactionItemResponse(
                    transactionType = "Withdraw",
                    timestamp = it.timestamp.toString(),
                    transactionAmount = it.transactionAmount,
                )
            }

            is TransferTransaction -> {
                if (it.sourceAccountId == accountId) {
                    TransactionItemResponse(
                        transactionType = "Transfer to account ${it.destinationAccountId}",
                        timestamp = it.timestamp.toString(),
                        transactionAmount = it.transactionAmount,
                    )
                } else if (it.destinationAccountId == accountId) {
                    TransactionItemResponse(
                        transactionType = "Received Transfer from ${it.sourceAccountId}",
                        timestamp = it.timestamp.toString(),
                        transactionAmount = it.transactionAmount,
                    )
                } else null
            }

            is DepositTransaction -> {
                TransactionItemResponse(
                    transactionType = "Deposit",
                    timestamp = it.timestamp.toString(),
                    transactionAmount = it.transactionAmount,
                )
            }

            else -> {
                null
            }
        }
    }
}