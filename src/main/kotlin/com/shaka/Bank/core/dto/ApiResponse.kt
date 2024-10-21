package com.shaka.Bank.core.dto

data class ApiResponse<T> (
    val data: T?,
    val message: String? = null,
)