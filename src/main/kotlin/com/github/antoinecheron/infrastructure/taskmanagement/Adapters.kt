package com.github.antoinecheron.infrastructure.taskmanagement

import com.github.antoinecheron.domain.taskmanagement.entities.TodoState

object Adapters {
    fun toTodo(row: TodoRow) = TodoState(row.id, row.title, row.completed)
    fun toTodoRow(todo: TodoState): TodoRow = TodoRow(todo.id, todo.title, todo.completed)
}
