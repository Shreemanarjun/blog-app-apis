package com.arjundev.blog.controller.category

import com.arjundev.blog.payloads.Categories
import com.arjundev.blog.payloads.CategoryDto
import com.arjundev.blog.payloads.DefaultResponseModel
import com.arjundev.blog.service.category.CategoryService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/api/category")
@RestController
@Tag(name = "Category")
@Validated
class CategoryController(@Autowired val categoryService: CategoryService) : ICategoryController {
    override suspend fun createCategory(categoryDto: CategoryDto): ResponseEntity<CategoryDto> {
        val createdcategoryDto = categoryService.createCategory(categoryDto)
        return ResponseEntity(createdcategoryDto, HttpStatus.CREATED)
    }

    override suspend fun updateCategory(categoryDto: CategoryDto, categoryId: Int): ResponseEntity<CategoryDto> {
        val updatedCategory = categoryService.updateCategory(categoryDto, categoryId)
        println("Update category called")
        return ResponseEntity.ok().body(updatedCategory)
    }

    override suspend fun deleteCategory(categoryId: Int): ResponseEntity<DefaultResponseModel> {
        categoryService.deleteCategory(categoryId)
        return ResponseEntity.ok().body(DefaultResponseModel(message = "Category deleted successfully", status = true))
    }

    override suspend fun getAllCategory( pageable: Pageable): ResponseEntity<Categories> {
        return ResponseEntity.ok(Categories(categories = categoryService.getAllCategory(pageable)))
    }

    override suspend fun getCategory(categoryId: Int): ResponseEntity<CategoryDto> {
        return ResponseEntity.ok(categoryService.getCategory(categoryId))
    }
}