package com.arjundev.blog.repository

import com.arjundev.blog.entities.Category
import com.arjundev.blog.entities.Post
import com.arjundev.blog.entities.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface IPostRepo : JpaRepository<Post, Int> {
    fun findByUser(user: User,pageable: Pageable): Page<Post>
    fun findByCategory(category: Category,pageable: Pageable):  Page<Post>

    fun findByTitleContainingIgnoreCase(title:String,pageable: Pageable): Page<Post>

}

