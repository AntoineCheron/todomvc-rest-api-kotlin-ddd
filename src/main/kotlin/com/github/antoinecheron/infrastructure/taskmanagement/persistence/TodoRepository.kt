package com.github.antoinecheron.infrastructure.taskmanagement.persistence

/**
 * This file contains a repository. Its role is to manage how the data is stored into the database.
 * Thus, it should manage as row data as possible and takes new states as input.
 * It must not implement any business logic. This must be in an Aggregate.
 * It must not have any knowledge of the commands.
 *
 */

// TODO: handle errors properly, with dedicated classes instead of ApiError

import kotlinx.coroutines.flow.*

import com.github.antoinecheron.application.restapi.ApiError
import com.github.antoinecheron.domain.taskmanagement.entities.TodoState
import com.github.antoinecheron.domain.taskmanagement.logic.updateTodo
import com.github.antoinecheron.infrastructure.taskmanagement.Status
import com.github.antoinecheron.infrastructure.taskmanagement.TodoRow
import com.github.antoinecheron.infrastructure.taskmanagement.fromTodo
import com.github.antoinecheron.infrastructure.taskmanagement.toTodo
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.r2dbc.core.*

import org.springframework.data.r2dbc.query.Criteria
import org.springframework.stereotype.Repository

const val TODO_TABLE_NAME = "todo"

@Repository
class TodoRepository (private val client: DatabaseClient) {

  suspend fun findById (id: String): TodoState =
    client.select().from(TODO_TABLE_NAME)
      .asType<TodoRow>()
      .matching(Criteria.where("id").`is`(id))
      .fetch()
      .awaitFirst()
      .toTodo()

  fun findAllWithStatus (status: Status): Flow<TodoState> {
    val query = if (status == Status.ALL) {
        client.select().from(TODO_TABLE_NAME).asType<TodoRow>()
      } else {
        val searchForCompletedTasks = status == Status.COMPLETED
        client.select().from(TODO_TABLE_NAME).asType<TodoRow>()
          .matching(Criteria.where("completed").`is`(searchForCompletedTasks))
    }

    return query.fetch().flow().map { row -> row.toTodo() }
  }

  suspend fun save(todo: TodoState): TodoState {
    val row = TodoRow.fromTodo(todo)

    client.execute("""
        INSERT INTO todo(id, title, completed) VALUES('${row.id}', '${row.title}', ${row.completed})
            ON DUPLICATE KEY UPDATE id='${row.id}';
      """)
      .await()

    return row.toTodo()
  }

  @Throws(ApiError::class)
  suspend fun delete (id: String): Unit {
    val rowsUpdated = client.delete().from(TODO_TABLE_NAME)
      .matching(Criteria.where("id").`is`(id)).fetch().rowsUpdated().awaitFirst()

    if (rowsUpdated == 0) {
      throw ApiError("Todo with id $id does not exist.", 404)
    }
  }

  suspend fun deleteByStatus (status: Status): Unit = if (status == Status.ALL) {
    client.delete().from(TODO_TABLE_NAME).fetch().rowsUpdated().awaitFirst()
    Unit
  } else {
    val searchForCompletedTasks = status == Status.COMPLETED
    client.delete().from<TodoRow>()
      .matching(Criteria.where("completed").`is`(searchForCompletedTasks))
      .fetch().rowsUpdated().awaitFirst()
    Unit
  }

}