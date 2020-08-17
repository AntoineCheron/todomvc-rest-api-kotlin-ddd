package com.github.antoinecheron.application.restapi

/**
 * This file must contain the models used by the REST API only.
 *
 * They must not be used in the other layers (infrastructure or domain).
 *
 */

import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

data class ApiError (override val message: String, val code: Int): Exception(message) {
  suspend fun toServerResponse () = ServerResponse.status(code).bodyValueAndAwait(ErrorPayload(message))
}

data class ErrorPayload (val error: String)

data class TodoUpdatePayload (val title: String, val completed: Boolean)
