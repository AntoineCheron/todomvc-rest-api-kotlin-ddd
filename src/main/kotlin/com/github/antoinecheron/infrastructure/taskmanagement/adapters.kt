package com.github.antoinecheron.infrastructure.taskmanagement

import com.github.antoinecheron.domain.taskmanagement.Todo

fun TodoRow.toTodo () = Todo(this.id, this.title, this.completed)
fun TodoRow.Companion.fromTodo (todo: Todo): TodoRow = TodoRow(todo.id, todo.title, todo.completed)