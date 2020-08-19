package com.github.antoinecheron.domain.taskmanagement.logic

import com.github.antoinecheron.domain.taskmanagement.entities.TodoState
import com.github.antoinecheron.domain.taskmanagement.entities.UpdateTodoCommand

fun updateTodo(id: String, command: UpdateTodoCommand): TodoState =
    TodoState(id, command.newTitle, command.newCompletedState)