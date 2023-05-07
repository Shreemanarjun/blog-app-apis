package com.arjundev.blog.repository

import com.arjundev.blog.entities.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface ICommentRepo : JpaRepository<Comment, Int>