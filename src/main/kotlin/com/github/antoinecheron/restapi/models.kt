package com.github.antoinecheron.restapi

import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

data class ApiError (override val message: String, val code: Int): Exception(message) {
  suspend fun toServerResponse () = ServerResponse.status(code).bodyValueAndAwait(ErrorPayload(message))
}

data class ErrorPayload (val error: String)

data class TodoCreationRequest (val title: String)

data class TodoUpdateRequest (val title: String, val completed: Boolean)
