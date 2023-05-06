package com.arjundev.blog.interceptor

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class ResponseTimeFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val startTime = System.currentTimeMillis()
        chain.doFilter(request, response)
        val endTime = System.currentTimeMillis()
        val responseTime = endTime - startTime
        (response as HttpServletResponse).addHeader("X-Response-Time", "$responseTime ms")
    }
}
