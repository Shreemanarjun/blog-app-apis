package com.arjundev.blog.service.user

import com.arjundev.blog.entities.User
import com.arjundev.blog.exception.ResourceNotFoundException
import com.arjundev.blog.payloads.UserDto
import com.arjundev.blog.payloads.toUser
import com.arjundev.blog.payloads.toUserDto
import com.arjundev.blog.repository.IUserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    val userRepo: IUserRepo,
    ) : IUserService {

    override suspend fun createUser(userDto: UserDto): UserDto {
        val user = userDto.toUser()
        val savedUser = userRepo.save(user)
        return savedUser.toUserDto()
    }

    override  suspend fun updateUser(userDto: UserDto, userId: Int): UserDto {
        val user = userRepo.findById(userId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "User",
                fieldName = "id",
                fieldValue = userId.toString()
            )
        }
        user.name = userDto.name
        user.email = userDto.email
        user.password = userDto.password
        user.about = userDto.about
        val updateUser = userRepo.save(user)
        return updateUser.toUserDto()
    }

    override suspend fun getUserById(userId: Int): UserDto {
        val user: User = userRepo.findById(userId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "User",
                fieldName = "id",
                fieldValue = userId.toString()
            )
        }
        return user.toUserDto()
    }

    override suspend fun getAllUser(pageable: Pageable): List<UserDto> {
        val users = userRepo.findAll(pageable)
        return users.map { user -> user.toUserDto() }.toList()
    }

    override suspend fun deleteUser(userId: Int) {
        val user: User = userRepo.findById(userId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "User",
                fieldName = "id",
                fieldValue = userId.toString()
            )
        }
        user.id?.let { userRepo.deleteById(it) }
    }

}

