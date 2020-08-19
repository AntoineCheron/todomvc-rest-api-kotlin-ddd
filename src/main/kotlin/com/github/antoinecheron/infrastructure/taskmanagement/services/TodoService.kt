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
import com.github.antoinecheron.domain.taskmanagement.entities.TodoCommand
import com.github.antoinecheron.domain.taskmanagement.entities.TodoState
import com.github.antoinecheron.domain.taskmanagement.logic.TodoAggregate
import com.github.antoinecheron.infrastructure.taskmanagement.Status
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class TodoService (private val todoRepository: TodoRepository) {

  fun list (status: Status): Flow<TodoState> =
    todoRepository.findAllWithStatus(status)

  suspend fun create (title: String): TodoState {
    val todo = TodoAggregate.create(title)
    return todoRepository.save(todo)
  }

  suspend fun handleAndSave (id: String, command: TodoCommand): TodoState {
    val newState = TodoAggregate.handle(id, command)
    return todoRepository.save(newState)
  }

  @Throws(ApiError::class)
  suspend fun delete (id: String) =
    todoRepository.delete(id)

  suspend fun deleteMany (status: Status) =
    todoRepository.deleteByStatus(status)
}