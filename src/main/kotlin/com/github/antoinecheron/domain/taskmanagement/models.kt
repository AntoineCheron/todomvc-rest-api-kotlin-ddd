package com.github.antoinecheron.domain.taskmanagement

/**
 * This file contains the models of the data management in the Task Management Bounded Context.
 *
 * Here is the main design of the concepts managed in this bounded context.
 * The models created to represent commands, persistence data, etc. are listed in other files.
 */

data class Todo (val id: String, val title: String, val completed: Boolean)

data class TodoCollection(val todos: Collection<Todo>)

enum class Status {
  ALL, COMPLETED, ACTIVE;

  companion object {

    fun of (s: String?): Status? = try {
        if (s != null) valueOf(s) else null
      } catch (e: IllegalArgumentException) {
        null
      }

  }
}
