package com.github.antoinecheron.infrastructure.taskmanagement

import com.github.antoinecheron.domain.taskmanagement.entities.TodoState

fun TodoRow.toTodo () = TodoState(this.id, this.title, this.completed)
fun TodoRow.Companion.fromTodo (todo: TodoState): TodoRow = TodoRow(todo.id, todo.title, todo.completed)