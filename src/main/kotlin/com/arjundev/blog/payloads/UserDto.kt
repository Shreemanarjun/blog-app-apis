package com.arjundev.blog.payloads

import com.arjundev.blog.entities.User

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size


data class Users(
    val users: List<UserDto>
)

@Schema(name = "User")
data class UserDto(
    val id: Int?,
    @get:Valid @field:Size(min = 4, message = "Username must be min of 4 character !!")
    val name: String,

    @get:Valid
    @field:Email(message = "Email address is not valid")
    val email: String,

    @field:Pattern(
        regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$ %^&*-]).{8,}\$",
        message = "Minimum eight characters, at least one upper case English letter, one lower case English letter, one number and one special character"
    )
    val password: String,

    @get:Valid @field:NotEmpty
    val about: String,
)

fun User.toUserDto(): UserDto = UserDto(
    id = id,
    name = name,
    email = email,
    password = password,
    about = about,

)

fun UserDto.toUser(): User = User(
    id = id,
    name =name,
    email = email,
    password = password,
    about =about,
)