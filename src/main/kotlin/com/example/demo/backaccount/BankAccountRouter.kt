package com.example.demo.backaccount

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class BankAccountRouter {
    val basePath = "/api/v1/bankaccount"

    @Bean
    fun bankAccountRoute(handler: BankAccountHandler) = coRouter {
        path(basePath).nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/history/not", handler::notHistory)
                GET("/history/maxBy", handler::maxBy)
            }
        }
    }
}