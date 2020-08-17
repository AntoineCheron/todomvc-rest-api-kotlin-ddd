package com.github.antoinecheron.domain.taskmanagement

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

import java.util.UUID

fun createTodo(createTodoCommand: CreateTodoCommand): Todo =
  Todo(generateId(), createTodoCommand.title, false)

fun updateTodo(command: UpdateTodoCommand): Todo =
  Todo(command.id, command.title, command.completed)

private fun generateId(): String = UUID.randomUUID().toString()