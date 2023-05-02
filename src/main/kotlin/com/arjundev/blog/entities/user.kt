package com.arjundev.blog.entities

import jakarta.persistence.*

@Entity
@Table(name = "users", schema = "blog_app_apis")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Int?=null,

    var name: String,

    var email: String,

    var password: String,

    var about: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var posts: MutableList<Post>? = mutableListOf(),
)

