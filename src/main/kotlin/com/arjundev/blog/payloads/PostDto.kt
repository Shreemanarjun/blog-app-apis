package com.arjundev.blog.payloads

import com.arjundev.blog.entities.Post

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import java.util.*




data class PostDto(
    val postId:Int?=null,
    @get:Valid  @field:NotEmpty
    val title: String?=null,
    @get:Valid  @field:NotEmpty
    val content: String?=null,
    val imageName: String? = "default.png",
    val addedDate: Date?=null,
    val category: CategoryDto?=null,
    val user: UserDto?=null

)

@Schema(name = "Post")
data class CreatePostRequest(
    @get:Valid  @field:NotEmpty
    val title: String,
    @get:Valid  @field:NotEmpty
    val content: String,
)

fun CreatePostRequest.toPostDto():PostDto=PostDto(title = title, content = content)

fun Post.toPostDto(): PostDto = PostDto(
    postId = postId,
    title = title,
    content =content,
    imageName=imageName,
    addedDate = addedDate,
    category = category?.toCategoryDto(),
    user = user?.toUserDto()
)

fun PostDto.toPost(): Post = Post(
    postId =  postId,
    title =  title,
    content =  content,
   imageName = imageName,
   addedDate =   addedDate?:Date(),
    category = null,
    user = user?.toUser()
)