package com.arjundev.blog

import com.arjundev.blog.repository.IUserRepo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BlogAppApisApplicationTests(
    @Autowired
    private val userRepo: IUserRepo
) {


    @Test
    fun contextLoads() {
    }

    @Test
    fun `repo test`() {
		println("${userRepo.javaClass.name} \n${userRepo.javaClass.packageName}")

    }

}
