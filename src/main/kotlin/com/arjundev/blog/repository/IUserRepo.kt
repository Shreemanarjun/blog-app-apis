package com.arjundev.blog.repository

import com.arjundev.blog.entities.User
import org.springframework.data.jpa.repository.JpaRepository


interface IUserRepo : JpaRepository<User, Int>
