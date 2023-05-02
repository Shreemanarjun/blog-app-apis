package com.arjundev.blog.exception

data class CustomValidationError(val errors: List<ValidationError>)

data class ValidationError(val fieldName:String,val message:String?)