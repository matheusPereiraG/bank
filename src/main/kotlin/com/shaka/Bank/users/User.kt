package com.shaka.Bank.users

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val isActive: Boolean,
)