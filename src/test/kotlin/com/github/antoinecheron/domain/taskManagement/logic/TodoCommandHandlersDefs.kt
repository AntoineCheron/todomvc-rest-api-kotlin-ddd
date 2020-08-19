package com.github.antoinecheron.domain.taskManagement.logic

import com.github.antoinecheron.domain.taskmanagement.entities.TodoState
import com.github.antoinecheron.domain.taskmanagement.entities.UpdateTodoCommand
import com.github.antoinecheron.domain.taskmanagement.logic.TodoAggregate
import io.cucumber.java8.En
import junit.framework.Assert

class TodoCommandHandlersDefs: En {

    init {
        var todo: TodoState

        Given("^I have to (\\d+)$") { title: String ->
            todo = TodoAggregate.create(title)
        }

        When("^I complete it$") { empty: String ->
            todo = TodoAggregate.handle(todo.id, UpdateTodoCommand(todo.title, true))
        }

        Then("^My todo should be completed$") {
            Assert.assertTrue(todo.completed)
        }
    }

}
