package com.arjundev.blog.payloads

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Sort

/** This class will be used for getting Pageable
 * Response which will accept generic type of content
 *
 */

@Schema(name = "Pageable")
data class PageableResponse<T>(
    val content: T,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
    val isLastPage: Boolean,
    val sort:MutableList<Sort.Order>
)