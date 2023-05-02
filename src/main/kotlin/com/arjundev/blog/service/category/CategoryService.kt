package com.arjundev.blog.service.category

import com.arjundev.blog.entities.Category
import com.arjundev.blog.exception.ResourceNotFoundException
import com.arjundev.blog.payloads.CategoryDto
import com.arjundev.blog.payloads.toCategory
import com.arjundev.blog.payloads.toCategoryDto
import com.arjundev.blog.repository.ICategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CategoryService(
    @Autowired
    val categoryRepo: ICategoryRepo
) : ICategoryService {
    override suspend fun createCategory(categoryDto: CategoryDto): CategoryDto {
        val category = categoryDto.toCategory()
        val savedCategory = categoryRepo.save(category)
        return savedCategory.toCategoryDto()
    }

    override suspend fun updateCategory(categoryDto: CategoryDto, categoryID: Int): CategoryDto {
        val category = categoryRepo.findById(categoryID).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "Category",
                fieldName = "Category Id",
                fieldValue = "$categoryID"
            )
        }
        category.categoryTitle = categoryDto.categoryTitle
        category.categoryDescription = categoryDto.categoryDescription
        println(category)
        val updatedCategory = categoryRepo.save(category)
        println(updatedCategory)
        return updatedCategory.toCategoryDto()
    }

    override suspend fun deleteCategory(categoryID: Int) {
        val category: Category = categoryRepo.findById(categoryID).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "Category",
                fieldName = "Category Id",
                fieldValue = "$categoryID"
            )
        }
        category.categoryID?.let { categoryRepo.deleteById(it) }
    }

    override suspend fun getCategory(categoryID: Int): CategoryDto {
        val category: Category = categoryRepo.findById(categoryID).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "Category",
                fieldName = "Category Id",
                fieldValue = "$categoryID"
            )
        }
        return category.toCategoryDto()
    }

    override suspend fun getAllCategory(pageable: Pageable): List<CategoryDto> {
        val categories = categoryRepo.findAll(pageable)
        return categories.map { category -> category.toCategoryDto() }.toList()
    }

}