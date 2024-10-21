package com.shaka.Bank.core.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiErrorResponse (
    val errorMsg: String,
    val details: Any? = null,
)