package com.github.antoinecheron.application.restapi.handlers

/**
 * This file contains a REST API command handler.
 *
 * The role of each function is to verify the incoming parameters, call the proper service
 * (only one service call is authorized per function) and then return the proper HTTP response (positive or error)
 *
 */

import com.github.antoinecheron.infrastructure.taskmanagement.services.TodoService
import com.github.antoinecheron.application.restapi.*
import com.github.antoinecheron.domain.taskmanagement.CreateTodoCommand
import com.github.antoinecheron.domain.taskmanagement.DeleteTodoCommand
import com.github.antoinecheron.domain.taskmanagement.UpdateTodoCommand

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import java.net.URI

@Component
class TodoHandler (private val todoService: TodoService) {

  suspend fun createTodo (serverRequest: ServerRequest): ServerResponse =
    try {
      val creationRequest = serverRequest.awaitBody<CreateTodoCommand>()
      validateTodoCreationRequest(creationRequest)

      val createdTodo = todoService.create(creationRequest)

      ServerResponse
        .created(URI.create("/todo/${createdTodo.id}"))
        .bodyValueAndAwait(createdTodo)
    } catch (error: ApiError) {
      error.toServerResponse()
    }

  suspend fun updateTodo (serverRequest: ServerRequest): ServerResponse =
    try {
      val id = serverRequest.pathVariable("id")
      val updateRequest = serverRequest.awaitBody<TodoUpdatePayload>()
      validateTodoUpdateRequest(updateRequest)

      val command = UpdateTodoCommand(id, updateRequest.title, updateRequest.completed)
      todoService.update(command)

      ServerResponse.noContent().buildAndAwait()
    } catch (error: ApiError) {
      error.toServerResponse()
    }

  suspend fun deleteTodo (serverRequest: ServerRequest): ServerResponse =
    try {
      val id = serverRequest.pathVariable("id")

      val command = DeleteTodoCommand(id)
      todoService.delete(command)

      ServerResponse.noContent().buildAndAwait()
    } catch (error: ApiError) {
      error.toServerResponse()
    }

}