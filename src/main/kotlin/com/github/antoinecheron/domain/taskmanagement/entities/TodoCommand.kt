package com.github.antoinecheron.domain.taskmanagement.entities

/**
 * This file contains the command that can be used on the Task Management Bounded Context.
 */

sealed class TodoCommand {}

data class UpdateTodoCommand (val newTitle: String, val newCompletedState: Boolean): TodoCommand()
