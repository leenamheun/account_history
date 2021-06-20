package com.example.demo.backaccount.service

import com.example.demo.backaccount.data.GetGroupBySumData
import com.example.demo.backaccount.data.GetNoTransactionYearRes

interface BankAccountService {
    fun notHistory(years: List<String>): MutableList<GetNoTransactionYearRes>
    fun maxBy(): MutableList<GetGroupBySumData>
}