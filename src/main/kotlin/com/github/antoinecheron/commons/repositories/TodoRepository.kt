package com.github.antoinecheron.commons.repositories

import kotlinx.coroutines.flow.*

import com.github.antoinecheron.commons.Status
import com.github.antoinecheron.commons.Todo
import com.github.antoinecheron.restapi.ApiError
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.r2dbc.core.*

import org.springframework.data.r2dbc.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class TodoRepository (private val client: DatabaseClient) {

  fun findAllWithStatus (status: Status): Flow<Todo> =
    if (status == Status.ALL) {
      client.select().from<Todo>().fetch().flow()
    } else {
      val searchForCompletedTasks = status == Status.COMPLETED
      client.select().from<Todo>()
        .matching(Criteria.where("completed").`is`(searchForCompletedTasks))
        .fetch()
        .flow()
    }

  suspend fun create (todo: Todo): Todo {
    client.insert().into<Todo>().using(todo).await()
    return todo
  }

  @Throws(ApiError::class)
  suspend fun update (todo: Todo): Todo {
    val rowsUpdated = client.update().table<Todo>().using(todo).fetch().rowsUpdated().awaitFirst()

    return if (rowsUpdated == 1) {
      todo
    } else {
      throw ApiError("Todo with id ${todo.id} does not exist.", 404)
    }
  }

  @Throws(ApiError::class)
  suspend fun delete (id: String): Unit {
    val rowsUpdated = client.delete().from<Todo>()
      .matching(Criteria.where("id").`is`(id)).fetch().rowsUpdated().awaitFirst()

    if (rowsUpdated == 0) {
      throw ApiError("Todo with id $id does not exist.", 404)
    }
  }

  suspend fun deleteByStatus (status: Status): Unit = if (status == Status.ALL) {
    client.delete().from<Todo>().fetch().rowsUpdated().awaitFirst()
    Unit
  } else {
    val searchForCompletedTasks = status == Status.COMPLETED
    client.delete().from<Todo>()
      .matching(Criteria.where("completed").`is`(searchForCompletedTasks))
      .fetch().rowsUpdated().awaitFirst()
    Unit
  }

}