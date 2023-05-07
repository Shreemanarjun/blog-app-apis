package com.arjundev.blog.payloads

import com.arjundev.blog.entities.Comment
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

@Schema(name = "Comment")
data class CommentDto(
    val id: Int?,
    @get:Valid @field:NotEmpty
    val content: String?
)

fun Comment.toCommentDto() = CommentDto(id = commentId, content = content)

fun CommentDto.toComment() = Comment(commentId = id, content = content, post = null)