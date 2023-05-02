package com.arjundev.blog.entities

import jakarta.persistence.*

@Entity
@Table(name = "categories", schema = "blog_app_apis")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var categoryID: Int? = null,
    @Column(name = "title", length = 100, nullable = false)
    var categoryTitle: String,
    @Column(name = "description")
    var categoryDescription: String,
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL])
    var posts: MutableList<Post> = mutableListOf(),
)


