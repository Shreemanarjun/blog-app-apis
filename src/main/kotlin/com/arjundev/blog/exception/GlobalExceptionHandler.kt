package com.arjundev.blog.exception

import org.springframework.data.mapping.PropertyReferenceException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException


@ControllerAdvice
class GlobalExceptionHandler {
    companion object {
        private val CLASS_PATTERN = """.*Required request body is missing:.*public.*\(([^)]*)\)""".toRegex()
        private val CLASS_NAME_PATTERN = """(?:.*\.)?(\w+)""".toRegex()
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun resourceNotFoundExceptionHandler(ex: ResourceNotFoundException): ResponseEntity<ProblemDetail> {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.toString())).build()

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    fun requestMethodNotSupportedException(ex: HttpRequestMethodNotSupportedException): ResponseEntity<ProblemDetail> {
        val supportedMethods = ex.supportedMethods?.map { it }
        return ResponseEntity.of(
            ProblemDetail.forStatusAndDetail(
                HttpStatus.METHOD_NOT_ALLOWED,
                "${ex.method} method not supported.Only supported methods are $supportedMethods"
            )
        ).build()
    }


    @ExceptionHandler(PropertyReferenceException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun propertyReferenceExceptionHandler(ex: PropertyReferenceException): ResponseEntity<ProblemDetail> {

        return ResponseEntity.of(
            ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "${ex.propertyName} property not found."
            )
        ).build()
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMismatchExceptionHandler(ex: MethodArgumentTypeMismatchException): ResponseEntity<ProblemDetail> {
        val responseEntity =
            ResponseEntity.of(ProblemDetail.forStatus(409).apply {
                detail =
                    "`${ex.parameter.parameterName}`of type `${ex.requiredType}` needed where you send `${ex.value.toString()}` with type `${ex.value?.javaClass?.simpleName}`"
            }).build<ProblemDetail>()
        return responseEntity

    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpMessageNotReadableExceptionHandler(
        ex: HttpMessageNotReadableException,
        webRequest: WebRequest
    ): ResponseEntity<ProblemDetail> {
        val message = ex.message
        val matchResult = message?.let { CLASS_PATTERN.find(it) }
        val classNames =
            matchResult?.groupValues?.get(1)?.split(",")?.map { CLASS_NAME_PATTERN.find(it)?.groupValues?.get(1) ?: it }
                ?: listOf("unknown parameter types")
        val errorMessage = "Invalid request body for ${classNames.toSet().joinToString()}"
        val responseEntity =
            ResponseEntity.of(ProblemDetail.forStatus(HttpStatus.BAD_REQUEST).apply {
                detail = errorMessage

            }).build<ProblemDetail>()
        return responseEntity
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun methodArgumentNotValidExceptionHandler(ex: MethodArgumentNotValidException): ResponseEntity<CustomValidationError> {

        val errors: MutableList<ValidationError> = mutableListOf()
        for (error in ex.bindingResult.fieldErrors) {
            errors.add(ValidationError(fieldName = error.field, message = error.defaultMessage))

        }
        for (error in ex.bindingResult.globalErrors) {
            errors.add(ValidationError(fieldName = error.objectName, message = error.defaultMessage))
        }
        return ResponseEntity(CustomValidationError(errors.toSet().toList()), HttpStatus.UNPROCESSABLE_ENTITY)
    }


}

