package com.example.demo.office

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class OfficeRouter {
    val basePath = "/api/v1/office"

    @Bean
    fun officeRoute(handler: OfficeHandler) = coRouter {
        path(basePath).nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/maxBy", handler::maxBy)
                GET("/sumBy", handler::sumBy)
            }
        }
    }
}