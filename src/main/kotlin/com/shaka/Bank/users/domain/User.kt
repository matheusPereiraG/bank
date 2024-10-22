package com.shaka.Bank.users.domain

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val isActive: Boolean,
)