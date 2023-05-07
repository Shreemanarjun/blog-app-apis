package com.arjundev.blog.entities

import jakarta.persistence.*

@Entity
@Table(name = "comments", schema = "blog_app_apis")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var commentId: Int? = null,

    var content: String? = null,
    @ManyToOne
    var post: Post? = null
)