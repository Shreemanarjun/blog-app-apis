package com.arjundev.blog.exception

data class ResourceNotFoundException(val resourceName: String, val fieldName: String, val fieldValue:String) :
    RuntimeException() {
    override fun toString(): String {
        return "$resourceName not found with $fieldName: $fieldValue"
    }
}