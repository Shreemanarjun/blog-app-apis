package com.arjundev.blog.controller.post

import com.arjundev.blog.payloads.*
import com.arjundev.blog.service.file.FileService
import com.arjundev.blog.service.post.PostService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springdoc.core.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StreamUtils
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.net.URI

@RestController
@RequestMapping("/api/")
@Tag(name = "Post")
@Validated
class PostController(
    @Autowired val postService: PostService,
    @Autowired val fileService: FileService,
) :
    IPostController {
    @Value("\${project.images}")

    lateinit var imagePath: String

    override suspend fun createPost(
        createPostRequest: CreatePostRequest,
        userId: Int,
        categoryId: Int
    ): ResponseEntity<PostDto> {
        val postDto =
            postService.createPost(postDto = createPostRequest.toPostDto(), userId = userId, categoryId = categoryId)
        return ResponseEntity.created(URI.create("/user/$userId/category/$categoryId/posts")).body(postDto)
    }

    override suspend fun getPostByUser(
        userId: Int,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<PageableResponse<List<PostDto>>> {
        val postdtos = postService.getPostByUser(userId, pageable)
        return ResponseEntity.ok(postdtos)
    }

    override suspend fun getPostByCategory(
        categoryId: Int,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<PageableResponse<List<PostDto>>> {
        val postdtos = postService.getPostByCategory(categoryId, pageable)
        return ResponseEntity.ok(postdtos)
    }

    override suspend fun getAllPost(@ParameterObject pageable: Pageable): ResponseEntity<PageableResponse<List<PostDto>>> {
        val postDtos = postService.getAllPosts(pageable)
        return ResponseEntity.ok(postDtos)
    }

    override suspend fun getPostById(postId: Int): ResponseEntity<PostDto> {
        val post = postService.getPostById(postId)
        return ResponseEntity.ok(post)
    }

    override suspend fun deletePost(postId: Int): ResponseEntity<DefaultResponseModel> {
        postService.deletePost(postId)
        return ResponseEntity.ok(DefaultResponseModel(status = true, message = "Post deleted successfully"))
    }

    override suspend fun updatePost(postDto: PostDto, postId: Int): ResponseEntity<PostDto> {
        val updatepostDto = postService.updatePost(postDto, postId)
        return ResponseEntity.ok(updatepostDto)
    }

    override suspend fun searchPosts(
        keywords: String,
        pageable: Pageable
    ): ResponseEntity<PageableResponse<List<PostDto>>> {
        val postDtos = postService.searchPosts(keywords, pageable)
        return ResponseEntity.ok(postDtos)
    }

    override suspend fun uploadPostImage(
        image: MultipartFile,
        postId: Int
    ): ResponseEntity<PostDto> {
        val post = postService.getPostById(postId = postId)
        post.imageName?.let { fileService.deleteExistingImage(imagePath, it) }
        val filename = fileService.uploadImage(imagePath, image)
        val updatePost = post.copy(imageName = filename)
        val updatedPost = postService.updatePost(updatePost, postId)
        return ResponseEntity.ok(updatedPost)
    }

    override suspend fun getPostImage(imageName: String, servletResponse: HttpServletResponse) {
        val resource = fileService.getResource(imagePath, imageName)
        servletResponse.contentType = MediaType.IMAGE_JPEG_VALUE
        StreamUtils.copy(resource, servletResponse.outputStream)

    }


}