package com.arjundev.blog.controller.comment

import com.arjundev.blog.payloads.CommentDto
import com.arjundev.blog.payloads.DefaultResponseModel
import com.arjundev.blog.service.comment.CommentService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
@Tag(name = "Comment")
@Validated
class CommentController(@Autowired val commentService: CommentService) : ICommentController {
    override suspend fun createComment(@RequestBody commentDto: CommentDto, postId: Int): ResponseEntity<CommentDto> {
        val createComment = commentService.createComment(commentDto, postId = postId)
        return ResponseEntity(createComment, HttpStatus.CREATED)
    }

    override suspend fun deleteComment(commentId: Int): ResponseEntity<DefaultResponseModel> {
        commentService.deleteComment(commentId)
        return ResponseEntity.ok().body(DefaultResponseModel(message = "Comment deleted successfully!!", status = true))
    }
}