package com.example.demo.backaccount.entity

import com.example.demo.common.entity.BaseEntity
import com.example.demo.office.entity.OfficeBankAccount
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

@Entity(name = "BankAccount")
class BankAccount(
    var name: String,
    var numbers: String,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    var officeAccount: MutableList<OfficeBankAccount> = mutableListOf(),
) : BaseEntity()