package com.github.antoinecheron.restapi.controllers

import com.github.antoinecheron.commons.Status
import com.github.antoinecheron.commons.TodoCollection
import com.github.antoinecheron.commons.services.TodoService
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class TodosHandler (private val todoService: TodoService) {

  suspend fun listTodos(request: ServerRequest): ServerResponse {
    val statusFromQuery = request.queryParamOrNull("status")
    val status = Status.of(statusFromQuery) ?: Status.ALL

    val todoList = todoService.list(status).toList()
    val bodyRepresentation = TodoCollection(todoList)

    return ServerResponse.ok().bodyValueAndAwait(bodyRepresentation)
  }

  suspend fun deleteManyByStatus(request: ServerRequest): ServerResponse {
    val statusFromQuery = request.queryParamOrNull("status")
    val status = Status.of(statusFromQuery) ?: Status.COMPLETED

    todoService.deleteMany(status)

    return ServerResponse.noContent().buildAndAwait()
  }

}