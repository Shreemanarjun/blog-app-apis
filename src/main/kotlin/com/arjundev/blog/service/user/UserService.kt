package com.arjundev.blog.service.user

import com.arjundev.blog.entities.User
import com.arjundev.blog.exception.ResourceNotFoundException
import com.arjundev.blog.payloads.UserDto
import com.arjundev.blog.repository.IUserRepo
import com.github.labai.utils.mapper.LaMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    val userRepo: IUserRepo,

) : IUserService {

    override fun createUser(userDto: UserDto): UserDto {
        val user = userDto.toUser()
        val savedUser = userRepo.save(user)
        return savedUser.toUserDto()
    }

    override fun updateUser(userDto: UserDto, userId: Int): UserDto {
        val user: User = userRepo.findById(userId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "User",
                fieldName = "id",
                fieldValue = userId.toString()
            )
        }
        val newUser =
            user.copy(name = userDto.name, email = userDto.email, password = userDto.password, about = userDto.about)
        val updateUser = userRepo.save(newUser)
        return updateUser.toUserDto()
    }

    override fun getUserById(userId: Int): UserDto {
        val user: User = userRepo.findById(userId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "User",
                fieldName = "id",
                fieldValue = userId.toString()
            )
        }
        return user.toUserDto()
    }

    override fun getAllUser(): List<UserDto> {
        val users = userRepo.findAll()
        return users.map { user -> user.toUserDto() }.toList()
    }

    override fun deleteUser(userId: Int) {
        val user: User = userRepo.findById(userId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "User",
                fieldName = "id",
                fieldValue = userId.toString()
            )
        }
        userRepo.deleteById(userId)
    }

    fun User.toUserDto() :UserDto= LaMapper.autoMapper<User,UserDto>().transform(this)
    fun UserDto.toUser():User = LaMapper.autoMapper<UserDto,User>().transform(this)
}