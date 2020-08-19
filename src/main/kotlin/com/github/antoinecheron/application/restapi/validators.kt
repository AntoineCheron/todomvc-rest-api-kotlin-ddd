package com.github.antoinecheron.application.restapi

import com.github.antoinecheron.application.restapi.utils.Http

/**
 * This file contains helper functions to validate the incoming HTTP requests
 *
 * They must not be used in the other layers (infrastructure or domain).
 *
 */

fun validateTodoCreationRequest(toValidate: CreateTodoPayload) =
    if (isValid(toValidate.title)) {
        toValidate
    } else {
        throw ApiError("Incorrect title, it must not be empty neither blank", Http.StatusCode.BAD_REQUEST)
    }

fun validateTodoUpdateRequest(toValidate: TodoUpdatePayload) =
    if (isValid(toValidate.title)) {
        toValidate
    } else {
        throw ApiError("Incorrect title, it must not be empty neither blank", Http.StatusCode.BAD_REQUEST)
    }

private fun isValid(s: String?): Boolean = s != null && s.isNotBlank() && s.isNotEmpty()
