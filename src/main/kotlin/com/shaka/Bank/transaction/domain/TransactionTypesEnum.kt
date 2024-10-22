package com.shaka.Bank.transaction.domain

enum class TransactionTypesEnum(name: String) {
    WITHDRAW("WITHDRAW"),
    DEPOSIT("DEPOSIT"),
    RECEIVED("RECEIVED"),
    SENT("SENT")
}