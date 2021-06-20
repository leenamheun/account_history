package com.example.demo.office.repository.service.impl

import com.example.demo.office.data.GetOfficeMaxByDetailRes
import com.example.demo.office.data.GetOfficeMaxByRes
import com.example.demo.office.repository.OfficeBankAccountRepository
import com.example.demo.office.repository.OfficeRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class OfficeServiceImplTest(
    @Autowired private val officeBackAccountRepository: OfficeBankAccountRepository,
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
            returnList.add( GetOfficeMaxByRes(year = key.toLong(), dataList = dataList) )
        }
        println(returnList.size)
        Assertions.assertTrue(returnList.get(0).year == 2018.toLong())
    }
}