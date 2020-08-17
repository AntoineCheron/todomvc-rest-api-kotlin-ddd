package com.github.antoinecheron.restapi.controllers

import com.github.antoinecheron.commons.services.TodoService
import com.github.antoinecheron.restapi.*

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.*
import java.net.URI

@Component
class TodoHandler (private val todoService: TodoService) {

  suspend fun createTodo (serverRequest: ServerRequest): ServerResponse =
    try {
      val creationRequest = serverRequest.awaitBody<TodoCreationRequest>()
      validateTodoCreationRequest(creationRequest)
      val createdTodo = todoService.create(creationRequest.title)

      ServerResponse
        .created(URI.create("/todo/${createdTodo.id}"))
        .bodyValueAndAwait(createdTodo)
    } catch (error: ApiError) {
      error.toServerResponse()
    }

  suspend fun updateTodo (serverRequest: ServerRequest): ServerResponse =
    try {
      val id = serverRequest.pathVariable("id")
      val updateRequest = serverRequest.awaitBody<TodoUpdateRequest>()
      validateTodoUpdateRequest(updateRequest)
      todoService.update(id, updateRequest.title, updateRequest.completed)
      ServerResponse.noContent().buildAndAwait()
    } catch (error: ApiError) {
      error.toServerResponse()
    }

  suspend fun deleteTodo (serverRequest: ServerRequest): ServerResponse =
    try {
      val id = serverRequest.pathVariable("id")
      todoService.delete(id)
      ServerResponse.noContent().buildAndAwait()
    } catch (error: ApiError) {
      error.toServerResponse()
    }

}