package com.github.antoinecheron.restapi

fun validateTodoCreationRequest (toValidate: TodoCreationRequest) =
  if (isValid(toValidate.title)) {
    toValidate
  } else {
    throw ApiError("Incorrect title, it must not be empty neither blank", 400)
  }

fun validateTodoUpdateRequest (toValidate: TodoUpdateRequest) =
  if (isValid(toValidate.title)) {
    toValidate
  } else {
    throw ApiError("Incorrect title, it must not be empty neither blank", 400)
  }

private fun isValid(s: String?): Boolean = s != null && s.isNotBlank() && s.isNotEmpty()