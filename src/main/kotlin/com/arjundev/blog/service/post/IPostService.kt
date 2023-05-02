package com.arjundev.blog.service.post

import com.arjundev.blog.payloads.PageableResponse
import com.arjundev.blog.payloads.PostDto
import org.springframework.data.domain.Pageable

interface IPostService {
    suspend fun createPost(postDto: PostDto,userId: Int,categoryId: Int): PostDto
    suspend fun updatePost(postDto: PostDto, postId: Int):PostDto

    suspend fun deletePost(postId: Int)

    suspend fun getAllPosts(pageable: Pageable): PageableResponse<List<PostDto>>
    suspend fun getPostById(postId: Int): PostDto

    suspend fun getPostByCategory(categoryId: Int,pageable: Pageable): PageableResponse<List<PostDto>>

    suspend fun getPostByUser(userId: Int,pageable: Pageable): PageableResponse<List<PostDto>>

   suspend fun searchPosts(keywords: String,pageable: Pageable):PageableResponse<List<PostDto>>
}