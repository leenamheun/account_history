package com.example.demo.office.repository

import com.example.demo.office.entity.Office
import org.springframework.data.jpa.repository.JpaRepository

interface OfficeRepository : JpaRepository<Office, Long> {
    fun findTopByCodeAndActive(code: String, active: Boolean): Office?
    fun findByIdAndActive(id: Long, active: Boolean): Office?
}