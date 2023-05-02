package com.arjundev.blog.controller.user

import com.arjundev.blog.payloads.DefaultResponseModel
import com.arjundev.blog.payloads.UserDto
import com.arjundev.blog.payloads.Users
import com.arjundev.blog.service.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/api/users")
@RestController
@Tag(name = "User")
@Validated
class UserController(@Autowired val userService: UserService) : IUserController {

    override suspend fun createUser(@RequestBody @Valid userDto: UserDto): ResponseEntity<UserDto> {
        val createduserDto = userService.createUser(userDto)
        return ResponseEntity(createduserDto,HttpStatus.CREATED)
    }


    override suspend fun updateUser(
        @Valid @RequestBody(required = true) userDto: UserDto,
        @PathVariable userId: Int,
    ): ResponseEntity<UserDto> {
        val updateduser = userService.updateUser(userDto, userId)
        return ResponseEntity.ok().body(updateduser)
    }


    override suspend fun deleteUser(@PathVariable userId: Int): ResponseEntity<DefaultResponseModel> {
        userService.deleteUser(userId)
        return ResponseEntity.ok(DefaultResponseModel(message = "User deleted successfully", status = true))
    }

    override suspend fun getAllUsers(@ParameterObject pageable: Pageable): ResponseEntity<Users> {
        return ResponseEntity.ok(Users(users = userService.getAllUser(pageable)))
    }


    override suspend fun getUser(@PathVariable userId: Int): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.getUserById(userId = userId))
    }


}