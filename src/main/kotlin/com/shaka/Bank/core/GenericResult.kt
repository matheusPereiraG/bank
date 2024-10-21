package com.shaka.Bank.core

sealed class GenericResult<out T> {
    data class Success<out T>(
        val data: T? = null,
    ) : GenericResult<T>()

    data class Error(
        val errorMsg: String,
    ) : GenericResult<Nothing>()
}