package com.example.demo.office.repository.service.impl

import com.example.demo.backaccount.repository.BankAccountRepository
import com.example.demo.history.repository.HistoryRepository
import com.example.demo.office.data.GetOfficeMaxByDetailRes
import com.example.demo.office.data.GetOfficeMaxByRes
import com.example.demo.office.data.GetOfficeSumByRes
import com.example.demo.office.repository.OfficeBankAccountRepository
import com.example.demo.office.repository.OfficeRepository
import com.example.demo.office.repository.OfficeTransferRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class OfficeServiceImplTest(
    @Autowired private val historyRepository: HistoryRepository,
    @Autowired private val bankAccountRepository: BankAccountRepository,
    @Autowired private val officeBackAccountRepository: OfficeBankAccountRepository,
    @Autowired private val officeTransferRepository: OfficeTransferRepository,
    @Autowired private val officeRepository: OfficeRepository
) {

    @Test
    fun findOfficeBankAccountByMaxPrice() {
        val groupingHistory = officeBackAccountRepository.findOfficeBankAccountByMaxPrice()
        val grouping = groupingHistory.groupBy { it.date }
        Assertions.assertTrue(grouping.size == 3)

        val returnList = mutableListOf<GetOfficeMaxByRes>()
        for (key in grouping.keys) {
            val dataList = grouping.get(key)!!.map {
                val officeInfo = officeRepository.findByIdAndActive(it.officeId.toLong(), true)
                GetOfficeMaxByDetailRes(sumAmt = it.sum.toLong(), brCode = officeInfo!!.code, brName = officeInfo.name)
            }.toMutableList()
            returnList.add(GetOfficeMaxByRes(year = key.toLong(), dataList = dataList))
        }
        Assertions.assertTrue(returnList.get(0).year == 2018.toLong())
    }

    @Test
    fun validateTotalPrice() {
        val testOffice = 1
        val officeBankAccountInfo = officeBackAccountRepository.findByOfficeId(testOffice.toLong())
        val accountList = officeBankAccountInfo.map { it.account.id!! }.toMutableList()

        val total = historyRepository.findByBankAccountId(accountList).map {
           val price = if(it.cancle == "N") (it.price!!.toInt() - it.fee!!.toInt())
                        else (it.price!!.toInt() - it.fee!!.toInt()) * -1
            price
        }.sumBy{ it }
        Assertions.assertTrue(total==-46486000)
    }

    @Test
    fun findOfficeByTransferWithNull() {
        val testOffice = "분당점"
        val officeInfo = officeRepository.findByNameAndActive(testOffice, true)
            ?: throw Exception()

        val officeSum = officeRepository.findOfficeByTransfer(officeInfo.id!!)?.let {
            GetOfficeSumByRes(sum = it.sum.toLong())
        }
        println(officeSum!!.sum)
    }

    @Test
    fun findOfficeByTransferWithNotNull() {
        val testOffice = "판교점"
        val officeInfo = officeRepository.findByNameAndActive(testOffice, true)
            ?: throw Exception()

        val officeSum = officeRepository.findOfficeByTransfer(officeInfo.id!!)?.let {
            GetOfficeSumByRes(sum = it.sum.toLong())
        }
        Assertions.assertTrue(officeSum!!.sum!!.toInt() == -140591300)
    }
}