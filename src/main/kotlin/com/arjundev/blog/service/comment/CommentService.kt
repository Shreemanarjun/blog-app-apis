package com.arjundev.blog.service.comment

import com.arjundev.blog.exception.ResourceNotFoundException
import com.arjundev.blog.payloads.CommentDto
import com.arjundev.blog.payloads.toComment
import com.arjundev.blog.payloads.toCommentDto
import com.arjundev.blog.repository.ICommentRepo
import com.arjundev.blog.repository.IPostRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService(
    @Autowired val postRepo: IPostRepo,
    @Autowired val commentRepo: ICommentRepo,
) : ICommentService {
    override suspend fun createComment(commentDto: CommentDto, postId: Int): CommentDto {
        val post = postRepo.findById(postId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "Posts",
                fieldName = "id",
                fieldValue = postId.toString()
            )
        }
        val comment = commentDto.toComment()
        comment.post = post
        val savedComment = commentRepo.save(comment)
        return savedComment.toCommentDto()
    }

    override suspend fun deleteComment(commentId: Int) {
        val comment = commentRepo.findById(commentId).orElseThrow {
            throw ResourceNotFoundException(
                resourceName = "Comments",
                fieldName = "id",
                fieldValue = commentId.toString()
            )
        }
        commentRepo.delete(comment)


    }
}