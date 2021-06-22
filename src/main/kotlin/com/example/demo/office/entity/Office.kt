package com.example.demo.office.entity

import com.example.demo.common.entity.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Office(
    var name: String,
    var code: String,
    @Enumerated(EnumType.STRING)
    var status: OfficeStatus?=OfficeStatus.RUNNING
) : BaseEntity()

enum class OfficeStatus{
    RUNNING, CLOSING, TRANSFERRING
}