package com.arjundev.blog.service.user

import com.arjundev.blog.payloads.UserDto
import org.springframework.data.domain.Pageable

interface IUserService {
    suspend fun createUser(userDto: UserDto): UserDto
    suspend fun updateUser(userDto: UserDto, userId: Int): UserDto
    suspend fun getUserById(userId: Int): UserDto
    suspend fun getAllUser(pageable: Pageable): List<UserDto>
    suspend fun deleteUser(userId: Int)

}