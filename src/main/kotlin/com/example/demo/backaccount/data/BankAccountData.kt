package com.example.demo.backaccount.data

data class GetNoTransactionYearRes(
    var year: Long?=0,
    var name: String?=null,
    var accNo: String?=null
)


data class GetGroupBySumData(
    var year: Long?=0,
    var name: String?=null,
    var accNo: String?=null,
    var sumAmt: Long?=0
)
