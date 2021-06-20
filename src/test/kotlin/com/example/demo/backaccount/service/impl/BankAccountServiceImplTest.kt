package com.example.demo.backaccount.service.impl

import com.example.demo.backaccount.repository.BankAccountRepository
import com.example.demo.history.repository.HistoryRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class BankAccountServiceImplTest(
    @Autowired private val historyRepository: HistoryRepository,
    @Autowired private val bankAccountRepository: BankAccountRepository
) {
    @Test
    fun historyByGroupingSum() {
        val groupingHistory = historyRepository.findGroupBySum()
        println(groupingHistory.toString())
        val grouping = groupingHistory.groupBy { it.date }
        Assertions.assertTrue(grouping.size == 3)
        for (key in grouping.keys) {
            val maxObject = grouping.get(key)!!.maxOfWith(compareBy({ it.sum }), { it })
            val maxAccount = bankAccountRepository.findByIdAndActive(maxObject.accountId.toLong(), true)

            if (key == "2018") Assertions.assertTrue(maxAccount.id == 45.toLong())
        }
    }
}