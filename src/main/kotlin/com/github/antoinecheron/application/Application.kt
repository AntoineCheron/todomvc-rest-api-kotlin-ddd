package com.github.antoinecheron.application

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(value = ["com.github.antoinecheron.infrastructure", "com.github.antoinecheron.application"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}