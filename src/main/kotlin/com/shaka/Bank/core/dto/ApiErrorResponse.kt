package com.shaka.Bank.core.dto

class ApiErrorResponse (
    val errorMsg: String,
    val details: Any? = null,
)