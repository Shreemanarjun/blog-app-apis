package com.arjundev.blog.repository

import com.arjundev.blog.entities.Category
import org.springframework.data.jpa.repository.JpaRepository


interface ICategoryRepo:JpaRepository<Category,Int>