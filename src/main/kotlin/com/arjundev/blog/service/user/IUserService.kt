package com.arjundev.blog.service.user

import com.arjundev.blog.payloads.UserDto

interface IUserService {
    fun createUser(userDto:UserDto):UserDto
    fun updateUser(userDto: UserDto,userId:Int):UserDto
    fun getUserById(userId:Int):UserDto
    fun getAllUser():List<UserDto>
    fun deleteUser(userId:Int)

}