package com.github.antoinecheron.domain.taskManagement.logic

import com.github.antoinecheron.domain.taskmanagement.entities.TodoState
import com.github.antoinecheron.domain.taskmanagement.entities.UpdateTodoCommand
import com.github.antoinecheron.domain.taskmanagement.logic.TodoAggregate
import com.github.antoinecheron.util.nonNullOrFail
import io.cucumber.java8.En
import junit.framework.Assert

class TodoCommandHandlersDefs: En {

    init {
        var maybeTodo: TodoState? = null

        Given("I have to {string}") { title: String ->
            maybeTodo = TodoAggregate.create(title)
        }

        When("^I complete it$") {
            nonNullOrFail(maybeTodo) { todo ->
                maybeTodo = TodoAggregate.handle(todo.id, UpdateTodoCommand(todo.title, true))
            }
        }

        Then("^My todo should be completed$") {
            nonNullOrFail(maybeTodo) { todo -> Assert.assertTrue(todo.completed) }
        }

    }

}
