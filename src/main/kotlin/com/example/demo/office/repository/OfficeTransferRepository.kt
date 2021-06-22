package com.example.demo.office.repository

import com.example.demo.office.entity.OfficeTransfer
import org.springframework.data.jpa.repository.JpaRepository

interface OfficeTransferRepository : JpaRepository<OfficeTransfer, Long> {

}