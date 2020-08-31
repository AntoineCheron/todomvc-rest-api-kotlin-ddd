package com.github.antoinecheron.domain.taskManagement.logic

import com.github.antoinecheron.domain.taskmanagement.entities.TodoState
import com.github.antoinecheron.domain.taskmanagement.entities.UpdateTodoCommand
import com.github.antoinecheron.domain.taskmanagement.logic.updateTodo
import org.junit.Test
import org.junit.jupiter.api.Assertions

class TodoCommandHandlersTest {
    @Test fun `when a todo is updated its id should stay unchanged` () {
        val id = "1234"
        val todo = TodoState(id, "title", false)
        val updatedTodo = updateTodo(id, UpdateTodoCommand("newTitle", false))
        Assertions.assertEquals(todo.id, updatedTodo.id)
    }

    @Test fun `this test should fail` () {
        Assertions.fail<Unit>("Voluntarily failed test")
    }
}

