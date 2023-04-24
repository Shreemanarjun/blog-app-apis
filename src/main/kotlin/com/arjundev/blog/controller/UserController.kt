package com.arjundev.blog.controller

import com.arjundev.blog.payloads.DefaultResponseModel
import com.arjundev.blog.payloads.UserDto
import com.arjundev.blog.service.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/users")
@Tag(name = "User")
class UserController(@Autowired val userService: UserService) {
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun createUser(@RequestBody userDto: UserDto): ResponseEntity<UserDto> {
        val createduserDto = userService.createUser(userDto)
        return ResponseEntity.created(URI.create("/api/users/")).body(createduserDto)
    }

    @PutMapping("/{userId}")
    fun updateUser(@RequestBody userDto: UserDto, @PathVariable userId: Int): ResponseEntity<UserDto> {
        val updateduser = userService.updateUser(userDto, userId)
        return ResponseEntity.ok().body(updateduser)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Int): ResponseEntity<DefaultResponseModel> {
        userService.deleteUser(userId)
        return ResponseEntity.ok(DefaultResponseModel(message = "User deleted successfully", status = true))
    }

    @GetMapping("/")
    fun getAllUsers(): ResponseEntity<List<UserDto>> {
        return ResponseEntity.ok(userService.getAllUser())
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Int): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.getUserById(userId = userId))
    }
}