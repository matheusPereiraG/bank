package com.shaka.Bank.transaction.utils

import com.shaka.Bank.transaction.domain.*
import com.shaka.Bank.transaction.dto.TransactionItemResponse


fun List<Transaction>.toResponseItems(accountId: Long): List<TransactionItemResponse> {
    return this.mapNotNull {
        when (it) {
            is WithdrawTransaction -> {
                TransactionItemResponse(
                    transactionType = TransactionTypesEnum.WITHDRAW.name,
                    timestamp = it.timestamp.toString(),
                    transactionAmount = it.transactionAmount,
                )
            }

            is TransferTransaction -> {
                if (it.sourceAccountId == accountId) {
                    TransactionItemResponse(
                        transactionType = TransactionTypesEnum.SENT.name,
                        timestamp = it.timestamp.toString(),
                        transactionAmount = it.transactionAmount,
                        fromAccount = it.sourceAccountId,
                        toAccount = it.destinationAccountId,

                        )
                } else if (it.destinationAccountId == accountId) {
                    TransactionItemResponse(
                        transactionType = TransactionTypesEnum.RECEIVED.name,
                        timestamp = it.timestamp.toString(),
                        transactionAmount = it.transactionAmount,
                        fromAccount = it.sourceAccountId,
                        toAccount = it.destinationAccountId,
                    )
                } else null
            }

            is DepositTransaction -> {
                TransactionItemResponse(
                    transactionType = TransactionTypesEnum.DEPOSIT.name,
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