package com.github.antoinecheron.domain.taskmanagement

/**
 * This file contains the command that can be used on the Task Management Bounded Context.
 */

data class CreateTodoCommand (val title: String)

data class ListTodoCommand (val status: Status)

data class UpdateTodoCommand (val id: String, val title: String, val completed: Boolean)

data class DeleteTodoCommand (val id: String)

data class DeleteManyTodoCommand (val status: Status)

