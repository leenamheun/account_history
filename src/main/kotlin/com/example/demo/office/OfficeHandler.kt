package com.example.demo.office

import com.example.demo.office.service.OfficeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class OfficeHandler(
    @Autowired private val officeService: OfficeService
) {
    suspend fun maxBy(request: ServerRequest): ServerResponse {
        return officeService.findOfficeByMaxPrice().let {
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(it)
        }
    }

    suspend fun sumBy(request: ServerRequest): ServerResponse {
        val name = if (request.queryParam("name").isPresent) request.queryParam("name").get() else ""
        return officeService.findOfficeBySumPrice(name).let {
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(it)
        }
    }
}
