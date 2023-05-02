package com.arjundev.blog.controller.category

import com.arjundev.blog.payloads.Categories
import com.arjundev.blog.payloads.CategoryDto
import com.arjundev.blog.payloads.DefaultResponseModel
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface ICategoryController {
    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    suspend fun createCategory(@RequestBody @Valid categoryDto: CategoryDto): ResponseEntity<CategoryDto>

    @PutMapping("/{categoryId}")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun updateCategory(
        @Valid @RequestBody(required = true) categoryDto: CategoryDto,
        @PathVariable categoryId: Int,
    ): ResponseEntity<CategoryDto>

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun deleteCategory(@PathVariable categoryId: Int): ResponseEntity<DefaultResponseModel>

    @GetMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun getAllCategory(@ParameterObject pageable: Pageable): ResponseEntity<Categories>

    @GetMapping("/{categoryId}")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun getCategory(@PathVariable categoryId: Int): ResponseEntity<CategoryDto>
}