package com.github.antoinecheron.infrastructure.taskmanagement.persistence

/**
 * This file contains a repository. Its role is to manage how the data is stored into the database.
 * Thus, it should manage as row data as possible.
 * It must not implement any business logic. This must be in an Aggregate.
 *
 */

// TODO: handle errors properly, with dedicated classes instead of ApiError

import kotlinx.coroutines.flow.*

import com.github.antoinecheron.domain.taskmanagement.Status
import com.github.antoinecheron.application.restapi.ApiError
import com.github.antoinecheron.infrastructure.taskmanagement.TodoRow
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.r2dbc.core.*

import org.springframework.data.r2dbc.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class TodoRepository (private val client: DatabaseClient) {

  fun findAllWithStatus (status: Status): Flow<TodoRow> =
    if (status == Status.ALL) {
      client.select().from<TodoRow>().fetch().flow()
    } else {
      val searchForCompletedTasks = status == Status.COMPLETED
      client.select().from<TodoRow>()
        .matching(Criteria.where("completed").`is`(searchForCompletedTasks))
        .fetch()
        .flow()
    }

  suspend fun create (todo: TodoRow): TodoRow {
    client.insert().into<TodoRow>().using(todo).await()
    return todo
  }

  @Throws(ApiError::class)
  suspend fun update (todo: TodoRow): TodoRow {
    val rowsUpdated = client.update().table<TodoRow>().using(todo).fetch().rowsUpdated().awaitFirst()

    return if (rowsUpdated == 1) {
      todo
    } else {
      throw ApiError("Todo with id ${todo.id} does not exist.", 404)
    }
  }

  @Throws(ApiError::class)
  suspend fun delete (id: String): Unit {
    val rowsUpdated = client.delete().from<TodoRow>()
      .matching(Criteria.where("id").`is`(id)).fetch().rowsUpdated().awaitFirst()

    if (rowsUpdated == 0) {
      throw ApiError("Todo with id $id does not exist.", 404)
    }
  }

  suspend fun deleteByStatus (status: Status): Unit = if (status == Status.ALL) {
    client.delete().from<TodoRow>().fetch().rowsUpdated().awaitFirst()
    Unit
  } else {
    val searchForCompletedTasks = status == Status.COMPLETED
    client.delete().from<TodoRow>()
      .matching(Criteria.where("completed").`is`(searchForCompletedTasks))
      .fetch().rowsUpdated().awaitFirst()
    Unit
  }

}