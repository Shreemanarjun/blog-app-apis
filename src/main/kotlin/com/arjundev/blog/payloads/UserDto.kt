package com.arjundev.blog.payloads

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@NoArgsConstructor
@Getter
@Setter
data class UserDto(
    val id: Int?,
    val name: String,
    val email: String,
    val password: String,
    val about: String
){
   init {

   }
}