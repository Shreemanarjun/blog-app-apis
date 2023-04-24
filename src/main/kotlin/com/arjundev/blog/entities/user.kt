package com.arjundev.blog.entities

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@Table(name = "users" ,schema ="blog_app_apis")
@NoArgsConstructor
@Getter
@Setter
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    var id: Int?,
    val name: String,
    val email:String,
    val password: String,
    val about: String,
)