package com.arjundev.blog.controller.post

import com.arjundev.blog.payloads.*
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface IPostController {
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    @ResponseStatus(code = HttpStatus.CREATED)
    suspend fun createPost(
        @RequestBody @Valid createPostRequest: CreatePostRequest,
        @PathVariable userId: Int,
        @PathVariable categoryId: Int
    ): ResponseEntity<PostDto>

    @GetMapping("/user/{userId}/posts")
    suspend fun getPostByUser(
        @PathVariable userId: Int,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<PageableResponse<List<PostDto>>>

    @GetMapping("/category/{categoryId}/posts")
    suspend fun getPostByCategory(
        @PathVariable categoryId: Int,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<PageableResponse<List<PostDto>>>

    @GetMapping("/posts")
    suspend fun getAllPost(@ParameterObject  pageable: Pageable): ResponseEntity<PageableResponse<List<PostDto>>>

    @GetMapping("/posts/{postId}")
    suspend fun getPostById(@PathVariable postId: Int): ResponseEntity<PostDto>

    @DeleteMapping("/posts/{postId}")
    suspend fun deletePost(@PathVariable postId: Int): ResponseEntity<DefaultResponseModel>

    @PutMapping("/posts/{postId}")
    suspend fun updatePost(@RequestBody @Valid postDto: PostDto, @PathVariable postId: Int): ResponseEntity<PostDto>

    @GetMapping("/posts/search/{keywords}")
    suspend fun searchPosts(@PathVariable keywords: String,@ParameterObject  pageable: Pageable):ResponseEntity<PageableResponse<List<PostDto>>>
}