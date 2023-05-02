package com.arjundev.blog.service.category

import com.arjundev.blog.payloads.CategoryDto
import org.springframework.data.domain.Pageable

interface ICategoryService {

    suspend fun createCategory(categoryDto: CategoryDto): CategoryDto
    suspend fun updateCategory(categoryDto: CategoryDto, categoryID: Int): CategoryDto
    suspend fun deleteCategory(categoryID: Int)
    suspend fun getCategory(categoryID: Int): CategoryDto
    suspend fun getAllCategory(pageable: Pageable): List<CategoryDto>

}