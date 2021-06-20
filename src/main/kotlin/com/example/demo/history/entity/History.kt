package com.example.demo.history.entity

import com.example.demo.backaccount.entity.BankAccount
import com.example.demo.common.entity.BaseEntity
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "History")
class History(
    var historyNo: Long? = 0,
    var date: String? = null,
    var price: Long? = 0,
    var fee: Long? = 0,
    var cancle: String = "N",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankAccount_id")
    var account: BankAccount,
) : BaseEntity()
