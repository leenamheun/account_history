package com.example.demo.office.entity

import com.example.demo.common.entity.BaseEntity
import javax.persistence.Entity

@Entity(name = "Office")
class Office(
    var name: String,
    var code: String
) : BaseEntity()