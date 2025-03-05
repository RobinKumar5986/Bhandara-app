package com.kgJr.bhandara.data.models

data class RestResponse<T>(
    val data: T?,
    val status: RestStatus?,
    val code: String?,
    val message: String
)
enum class RestStatus {
    SUCCESS,
    FAIL,
    ERROR,
    BAD_REQUEST,
    UNAUTHORIZED,
    VALIDATION_EXCEPTION,
    EXCEPTION,
    WRONG_CREDENTIALS,
    ACCESS_DENIED,
    NOT_FOUND,
    DUPLICATE_ENTITY
}
