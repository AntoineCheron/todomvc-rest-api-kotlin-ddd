package com.github.antoinecheron.domain.taskmanagement.logic

/**
 * This file is an aggregate in the sense of Domain Driven Design
 * (see <a href="https://martinfowler.com/bliki/DDD_Aggregate.html">Martin's Fowler definition</a>.
 *
 * Every function of an aggregate modify the state of a domain object.
 * Thus, it takes a state and a command a returns the new sense.
 *
 * Concretely, every public function of this file (named a command handler) must be in one of the following forms:
 * - fun name (command: CommandType): State -> when no previous state is necessary
 * - fun name (state: State, command: CommandType): State
 * - fun name (context: Context, state: State, command: CommandType): State
 *
 */

import com.github.antoinecheron.domain.taskmanagement.entities.TodoCommand
import com.github.antoinecheron.domain.taskmanagement.entities.TodoState
import com.github.antoinecheron.domain.taskmanagement.entities.UpdateTodoCommand
import java.util.UUID

class TodoAggregate {
    companion object {

        fun create(title: String): TodoState =
            TodoState(generateId(), title, false)

        fun handle(id: String, command: TodoCommand): TodoState =
            when (command) {
                is UpdateTodoCommand -> updateTodo(id, command)
            }
    }
}

private fun generateId(): String = UUID.randomUUID().toString()