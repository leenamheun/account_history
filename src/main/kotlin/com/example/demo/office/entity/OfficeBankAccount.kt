package com.example.demo.office.entity

import com.example.demo.backaccount.entity.BankAccount
import com.example.demo.common.entity.BaseEntity
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class OfficeBankAccount(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    var office: Office,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankAccount_id")
    var account: BankAccount,
) : BaseEntity()