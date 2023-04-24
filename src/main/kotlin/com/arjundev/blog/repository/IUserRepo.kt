package com.arjundev.blog.repository

import com.arjundev.blog.entities.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.MANDATORY)
interface IUserRepo:JpaRepository<User, Int>
