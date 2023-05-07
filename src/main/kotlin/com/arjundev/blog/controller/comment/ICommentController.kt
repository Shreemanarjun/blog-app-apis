package com.arjundev.blog.controller.comment

import com.arjundev.blog.payloads.CommentDto
import com.arjundev.blog.payloads.DefaultResponseModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus

interface ICommentController {
    @PostMapping("/post/{postId}/comments")
    @ResponseStatus(code = HttpStatus.CREATED)
    suspend fun createComment(commentDto: CommentDto, @PathVariable postId: Int): ResponseEntity<CommentDto>

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun deleteComment(@PathVariable commentId: Int): ResponseEntity<DefaultResponseModel>

}