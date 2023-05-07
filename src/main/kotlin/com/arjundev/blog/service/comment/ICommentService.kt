package com.arjundev.blog.service.comment

import com.arjundev.blog.payloads.CommentDto

interface ICommentService {
    suspend fun createComment(commentDto: CommentDto,postId:Int):CommentDto
    suspend fun deleteComment(commentId:Int)

}