package com.github.antoinecheron.commons

import org.springframework.data.annotation.Id

data class Todo (@Id val id: String, val title: String, val completed: Boolean)

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
