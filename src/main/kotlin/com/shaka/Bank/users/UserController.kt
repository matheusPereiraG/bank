package com.shaka.Bank.users

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val usersRepository: UsersRepository) {
    @PostMapping("/user")
    fun newUser(@RequestBody newUser: User) {
        usersRepository.insertUsers(newUser)
    }

}