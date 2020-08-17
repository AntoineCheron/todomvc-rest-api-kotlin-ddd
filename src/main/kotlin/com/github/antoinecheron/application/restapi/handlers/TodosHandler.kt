package com.github.antoinecheron.application.restapi.handlers

/**
 * This file contains a REST API command handler.
 *
 * The role of each function is to verify the incoming parameters, call the proper service
 * (only one service call is authorized per function) and then return the proper HTTP response (positive or error)
 *
 */

import com.github.antoinecheron.domain.taskmanagement.DeleteManyTodoCommand
import com.github.antoinecheron.domain.taskmanagement.ListTodoCommand
import com.github.antoinecheron.domain.taskmanagement.Status
import com.github.antoinecheron.domain.taskmanagement.TodoCollection
import com.github.antoinecheron.infrastructure.taskmanagement.services.TodoService

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class TodosHandler (private val todoService: TodoService) {

  suspend fun listTodos(request: ServerRequest): ServerResponse {
    val statusFromQuery = request.queryParamOrNull("status")
    val status = Status.of(statusFromQuery) ?: Status.ALL
    val command = ListTodoCommand(status)

    val todoList = todoService.list(command).toList()
    val bodyRepresentation = TodoCollection(todoList)

    return ServerResponse.ok().bodyValueAndAwait(bodyRepresentation)
  }

  suspend fun deleteManyByStatus(request: ServerRequest): ServerResponse {
    val statusFromQuery = request.queryParamOrNull("status")
    val status = Status.of(statusFromQuery) ?: Status.COMPLETED
    val command = DeleteManyTodoCommand(status)

    todoService.deleteMany(command)

    return ServerResponse.noContent().buildAndAwait()
  }

}