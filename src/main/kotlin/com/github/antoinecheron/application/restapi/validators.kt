package com.github.antoinecheron.application.restapi

/**
 * This file contains helper functions to validate the incoming HTTP requests
 *
 * They must not be used in the other layers (infrastructure or domain).
 *
 */

import com.github.antoinecheron.domain.taskmanagement.CreateTodoCommand

fun validateTodoCreationRequest (toValidate: CreateTodoCommand) =
  if (isValid(toValidate.title)) {
    toValidate
  } else {
    throw ApiError("Incorrect title, it must not be empty neither blank", 400)
  }

fun validateTodoUpdateRequest (toValidate: TodoUpdatePayload) =
  if (isValid(toValidate.title)) {
    toValidate
  } else {
    throw ApiError("Incorrect title, it must not be empty neither blank", 400)
  }

private fun isValid(s: String?): Boolean = s != null && s.isNotBlank() && s.isNotEmpty()