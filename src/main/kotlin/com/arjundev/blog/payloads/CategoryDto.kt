package com.arjundev.blog.payloads

import com.arjundev.blog.entities.Category
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class Categories(
    val categories: List<CategoryDto>
)

@Schema(name = "Category")
data class CategoryDto(
    val categoryID: Int?=null,
    @get:Valid @field:NotBlank @field:Size(min = 4, message = "must be min of 4 character")
    val categoryTitle: String,
    @get:Valid @field:NotBlank @field:Size(min = 10, message = "must be min of 10 character")
    val categoryDescription: String,

)


fun Category.toCategoryDto(): CategoryDto =
    CategoryDto(
        categoryID = categoryID,
        categoryTitle = categoryTitle,
        categoryDescription = categoryDescription,

    )

fun CategoryDto.toCategory(): Category = Category(
    categoryID = categoryID, categoryTitle = categoryTitle, categoryDescription = categoryDescription,
)