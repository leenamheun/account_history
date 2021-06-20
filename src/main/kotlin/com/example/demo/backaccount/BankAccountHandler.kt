package com.example.demo.backaccount

import com.example.demo.backaccount.service.BankAccountService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class BankAccountHandler(
    private val bankAccountService: BankAccountService
) {
    suspend fun notHistory(request: ServerRequest): ServerResponse {
         val years = mutableListOf("2018", "2019")
        return bankAccountService.notHistory(years).let {
          ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(it)
      }
     }

     suspend fun maxBy(request: ServerRequest): ServerResponse {
         return bankAccountService.maxBy().let {
             ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(it)
         }
     }
}
