package com.shaka.Bank.core.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T> (
    val data: T? = null,
    val message: String? = null,
)