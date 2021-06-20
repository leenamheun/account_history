package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@EnableJpaAuditing
@SpringBootApplication
/*@EntityScan(
    "com.example.demo.history.entity.History"
//    "com.example.demo.bankaccount.entity.BankAccount",
//    "com.example.demo.common.entity.BaseEntity",
//
//    "com.example.demo.office.entity.Office"
)*/
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
