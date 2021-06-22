package com.example.demo.office.entity

import com.example.demo.common.entity.BaseEntity
import javax.persistence.*

@Entity
class OfficeTransfer(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_source_id")
    var officeSource: Office,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_target_id")
    var officeTarget: Office,
) : BaseEntity()

