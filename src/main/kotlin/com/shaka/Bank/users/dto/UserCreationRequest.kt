package com.shaka.Bank.users.dto

import jakarta.validation.constraints.NotBlank


data class UserCreationRequest(
    @field:NotBlank(message = "First name is required")
    val firstName: String,

    @field:NotBlank(message = "Last name is required")
    val lastName: String
)