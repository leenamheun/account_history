package com.example.demo.backaccount.service.impl

import com.example.demo.backaccount.data.GetGroupBySumData
import com.example.demo.backaccount.data.GetNoTransactionYearRes
import com.example.demo.backaccount.repository.BankAccountRepository
import com.example.demo.backaccount.service.BankAccountService
import com.example.demo.history.repository.HistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BankAccountServiceImpl(
    private val historyRepository: HistoryRepository,
    private val bankAccountRepository: BankAccountRepository
) : BankAccountService {
    @Transactional(readOnly = true)
    override fun notHistory(years: List<String>): MutableList<GetNoTransactionYearRes> {
        val returnList = mutableListOf<GetNoTransactionYearRes>()
        years.forEach {
            val item = bankAccountRepository.findByYearAndActive(it).let { list ->
                list.map { ba ->
                    GetNoTransactionYearRes(year = it.toLong(), ba.name, ba.numbers)
                }
            }
            returnList.addAll(item)
        }
        return returnList
    }

    @Transactional(readOnly = true)
    override fun maxBy(): MutableList<GetGroupBySumData> {
        val returnList = mutableListOf<GetGroupBySumData>()
        val groupingHistory = historyRepository.findGroupBySum()
        val grouping = groupingHistory.groupBy { it.date }

        for (key in grouping.keys) {
            val maxObject = grouping.get(key)!!.maxOfWith(compareBy({ it.sum }), { it })
            val maxAccount = bankAccountRepository.findByIdAndActive(maxObject.accountId.toLong(), true)
            returnList.add(
                GetGroupBySumData(
                    year = key.toLong(),
                    name = maxAccount.name,
                    accNo = maxAccount.numbers,
                    sumAmt = maxObject.sum.toLong()
                )
            )
        }
        return returnList
    }
}