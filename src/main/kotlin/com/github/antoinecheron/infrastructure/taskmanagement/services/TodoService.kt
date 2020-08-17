package com.github.antoinecheron.infrastructure.taskmanagement.services

/**
 * This file contains a service. The role of a service is to technically connect an Aggregate with a Repository.
 * It must not implement any business logic. This must be in an Aggregate.
 *
 * Concretely, every public function of this file must be in one of the following forms:
 * - fun name (command: CommandType): State -> when no previous state is necessary
 * - fun name (state: State, command: CommandType): State
 * - fun name (context: Context, state: State, command: CommandType): State
 *
 */

import com.github.antoinecheron.infrastructure.taskmanagement.persistence.TodoRepository
import com.github.antoinecheron.application.restapi.ApiError
import com.github.antoinecheron.domain.taskmanagement.*
import com.github.antoinecheron.infrastructure.taskmanagement.TodoRow
import com.github.antoinecheron.infrastructure.taskmanagement.fromTodo
import com.github.antoinecheron.infrastructure.taskmanagement.toTodo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class TodoService (private val todoRepository: TodoRepository) {

  fun list (command: ListTodoCommand): Flow<Todo> =
    todoRepository.findAllWithStatus(command.status).map { todoRow -> todoRow.toTodo() }

  suspend fun create (command: CreateTodoCommand): Todo {
    val todoRow = TodoRow.fromTodo(createTodo(command))
    val createdTodo = todoRepository.create(todoRow)
    return createdTodo.toTodo()
  }

  @Throws(ApiError::class)
  suspend fun update (command: UpdateTodoCommand) =
    todoRepository.update(TodoRow.fromTodo(updateTodo(command))).toTodo()

  @Throws(ApiError::class)
  suspend fun delete (command: DeleteTodoCommand) =
    todoRepository.delete(command.id)

  suspend fun deleteMany (command: DeleteManyTodoCommand) =
    todoRepository.deleteByStatus(command.status)
}