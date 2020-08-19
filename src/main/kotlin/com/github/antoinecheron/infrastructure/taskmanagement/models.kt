package com.github.antoinecheron.infrastructure.taskmanagement

/**
 * This file contains the models used by the service and persistence layers.
 * No conversion should be done here. These are implemented in the <a href="file:./adapters.kt">adapters.kt</a> file.
 */

import org.springframework.data.annotation.Id

data class TodoRow (@Id val id: String, val title: String, val completed: Boolean) {
  companion object { }
}

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