package com.shaka.Bank.core.dto

data class ApiResponse<T> (
    val data: T? = null,
    val message: String? = null,
)