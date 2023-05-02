package com.arjundev.blog.controller.user

import com.arjundev.blog.payloads.DefaultResponseModel
import com.arjundev.blog.payloads.UserDto
import com.arjundev.blog.payloads.Users
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


interface IUserController {
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)

    suspend fun createUser(@RequestBody @Valid userDto: UserDto): ResponseEntity<UserDto>

    @PutMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun updateUser(
        @Valid @RequestBody(required = true) userDto: UserDto,
        @PathVariable userId: Int,
    ): ResponseEntity<UserDto>

    @DeleteMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun deleteUser(@PathVariable userId: Int): ResponseEntity<DefaultResponseModel>

    @GetMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun getAllUsers(@ParameterObject pageable: Pageable): ResponseEntity<Users>

    @GetMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.OK)

    suspend fun getUser(@PathVariable userId: Int): ResponseEntity<UserDto>
}