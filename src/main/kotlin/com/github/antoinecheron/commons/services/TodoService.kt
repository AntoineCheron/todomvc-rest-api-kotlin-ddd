package com.github.antoinecheron.commons.services

import com.github.antoinecheron.commons.Status
import com.github.antoinecheron.commons.Todo
import com.github.antoinecheron.commons.repositories.TodoRepository
import com.github.antoinecheron.restapi.ApiError
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import java.util.*

@Service
class TodoService (private val todoRepository: TodoRepository) {

  fun list (status: Status): Flow<Todo> =
    todoRepository.findAllWithStatus(status)

  suspend fun create (title: String) =
    todoRepository.create(
      Todo(generateId(), title, false)
    )

  @Throws(ApiError::class)
  suspend fun update (id: String, title: String, completed: Boolean) =
    todoRepository.update(Todo(id, title, completed))

  @Throws(ApiError::class)
  suspend fun delete (id: String) = todoRepository.delete(id)

  suspend fun deleteMany (status: Status) =
    todoRepository.deleteByStatus(status)

  private fun generateId(): String = UUID.randomUUID().toString()
}