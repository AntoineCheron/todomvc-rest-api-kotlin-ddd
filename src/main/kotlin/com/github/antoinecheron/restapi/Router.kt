package com.github.antoinecheron.restapi

import com.github.antoinecheron.restapi.controllers.TodoHandler
import com.github.antoinecheron.restapi.controllers.TodosHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
open class Router {

  @Bean
  open fun routes(todoHandler: TodoHandler, todosHandler: TodosHandler) = coRouter {
    accept(MediaType.APPLICATION_JSON).nest {

      GET("/rest/todos", todosHandler::listTodos)
      POST("/rest/todo", todoHandler::createTodo)
      PUT("/rest/todo/{id}", todoHandler::updateTodo)
      DELETE("/rest/todo/{id}", todoHandler::deleteTodo)
      DELETE("/rest/todos", todosHandler::deleteManyByStatus)

    }
  }

}