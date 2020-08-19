package com.github.antoinecheron.domain.taskManagement.logic

import com.github.antoinecheron.domain.taskmanagement.entities.TodoState
import com.github.antoinecheron.domain.taskmanagement.entities.UpdateTodoCommand
import com.github.antoinecheron.domain.taskmanagement.logic.updateTodo
import org.junit.Test
import org.junit.jupiter.api.Assertions

class TodoCommandHandlersTest {
    @Test fun updatedTodoShouldKeepId () {
        val id = "1234"
        val todo = TodoState(id, "title", false)
        val updatedTodo = updateTodo(id, UpdateTodoCommand("newTitle", false))
        Assertions.assertEquals(todo.id, updatedTodo.id)
    }
}

