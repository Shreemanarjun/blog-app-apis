package com.arjundev.blog.service.post

import com.arjundev.blog.exception.ResourceNotFoundException
import com.arjundev.blog.payloads.PageableResponse
import com.arjundev.blog.payloads.PostDto
import com.arjundev.blog.payloads.toPost
import com.arjundev.blog.payloads.toPostDto
import com.arjundev.blog.repository.ICategoryRepo
import com.arjundev.blog.repository.IPostRepo
import com.arjundev.blog.repository.IUserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostService(
    @Autowired val postRepo: IPostRepo,
    @Autowired val userRepo: IUserRepo,
    @Autowired val categoryRepo: ICategoryRepo,
) : IPostService {
    override suspend fun createPost(postDto: PostDto, userId: Int, categoryId: Int): PostDto {
        val currentUser = userRepo.findById(userId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "User",
                fieldName = "id",
                fieldValue = userId.toString()
            )
        }

        val userCategory = categoryRepo.findById(categoryId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "Category",
                fieldName = "id",
                fieldValue = categoryId.toString()
            )
        }
        val post = postDto.toPost().apply {
            imageName = "default.png"
            user = currentUser
            category = userCategory
            addedDate = Date()
        }
        val updatedPost = postRepo.save(post)
        return updatedPost.toPostDto()

    }

    override suspend fun updatePost(postDto: PostDto, postId: Int): PostDto {
        val post = postRepo.findById(postId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "Post",
                fieldName = "id",
                fieldValue = postId.toString()
            )
        }
        val newPost = post.apply {
            title = postDto.title
            content = postDto.content
            imageName = postDto.imageName
        }
        val updatepost = postRepo.save(newPost)
        return updatepost.toPostDto()
    }

    override suspend fun deletePost(postId: Int) {
        val post = postRepo.findById(postId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "Post",
                fieldName = "id",
                fieldValue = postId.toString()
            )
        }
        postRepo.delete(post)
    }

    override suspend fun getAllPosts(pageable: Pageable): PageableResponse<List<PostDto>> {
        val allPosts = postRepo.findAll(pageable)
        return PageableResponse(
            content = allPosts.map { it.toPostDto() }.toList(),
            pageNumber = allPosts.number,
            pageSize = allPosts.size,
            totalElements = allPosts.totalElements,
            totalPages = allPosts.totalPages,
            isLastPage = allPosts.isLast,
            sort = allPosts.sort.toList()
        )
    }

    override suspend fun getPostById(postId: Int): PostDto {
        val post = postRepo.findById(postId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "Posts",
                fieldName = "id",
                fieldValue = postId.toString()
            )
        }
        return post.toPostDto()
    }

    override suspend fun getPostByCategory(categoryId: Int, pageable: Pageable): PageableResponse<List<PostDto>> {
        val category = this.categoryRepo.findById(categoryId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "Category",
                fieldName = "id",
                fieldValue = categoryId.toString()
            )
        }
        val allPosts = postRepo.findByCategory(category, pageable)
        return PageableResponse(
            content = allPosts.map { it.toPostDto() }.toList(),
            pageNumber = allPosts.number,
            pageSize = allPosts.size,
            totalElements = allPosts.totalElements,
            totalPages = allPosts.totalPages,
            isLastPage = allPosts.isLast,
            sort = allPosts.sort.toList()
        )
    }

    override suspend fun getPostByUser(userId: Int, pageable: Pageable): PageableResponse<List<PostDto>> {
        val currentUser = userRepo.findById(userId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "User",
                fieldName = "id",
                fieldValue = userId.toString()
            )
        }
        val allPosts = postRepo.findByUser(currentUser, pageable)
        return PageableResponse(
            content = allPosts.map { it.toPostDto() }.toList(),
            pageNumber = allPosts.number,
            pageSize = allPosts.size,
            totalElements = allPosts.totalElements,
            totalPages = allPosts.totalPages,
            isLastPage = allPosts.isLast,
            sort = allPosts.sort.toList()
        )
    }

    override suspend fun searchPosts(keywords: String,pageable: Pageable):PageableResponse<List<PostDto>> {
        val allPosts = postRepo.findByTitleContainingIgnoreCase(title = keywords, pageable = pageable)
        return PageableResponse(
            content = allPosts.map { it.toPostDto() }.toList(),
            pageNumber = allPosts.number,
            pageSize = allPosts.size,
            totalElements = allPosts.totalElements,
            totalPages = allPosts.totalPages,
            isLastPage = allPosts.isLast,
            sort = allPosts.sort.toList()
        )
    }


}