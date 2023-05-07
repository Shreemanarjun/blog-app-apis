package com.arjundev.blog.entities

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "posts", schema = "blog_app_apis")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var postId: Int? = null,

    @Column(name = "post_title", length = 100, nullable = false)
    var title: String? = null,

    @Column(length = 1000000000)
    var content: String? = null,
    var imageName: String? = null,
    var addedDate: Date? = null,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null,

    @ManyToOne
    var user: User? = null,
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL])
    var comments: MutableSet<Comment>? = mutableSetOf()
)



