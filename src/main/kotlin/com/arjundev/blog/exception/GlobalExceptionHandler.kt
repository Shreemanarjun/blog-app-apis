package com.arjundev.blog.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundExceptionHandler(ex: ResourceNotFoundException): ResponseEntity<Any> {
        val responseEntity =
            ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.toString())).build<Any>()
        return responseEntity

    }
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMismatchExceptionHandler(ex: MethodArgumentTypeMismatchException): ResponseEntity<String> {
        val responseEntity =
            ResponseEntity.of(ProblemDetail.forStatus(404).apply {
                detail = "`${ex.parameter.parameterName}`of type `${ex.requiredType}` needed where you send `${ex.value.toString()}` with type `${ex.value?.javaClass?.simpleName}`"
            }).build<String>()
        return responseEntity

    }

}