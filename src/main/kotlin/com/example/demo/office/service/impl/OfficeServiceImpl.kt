package com.example.demo.office.service.impl

import com.example.demo.office.data.GetOfficeMaxByDetailRes
import com.example.demo.office.data.GetOfficeMaxByRes
import com.example.demo.office.repository.OfficeBankAccountRepository
import com.example.demo.office.repository.OfficeRepository
import com.example.demo.office.service.OfficeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OfficeServiceImpl(
    @Autowired private val officeBackAccountRepository: OfficeBankAccountRepository,
    @Autowired private val officeRepository: OfficeRepository
) : OfficeService {
    @Transactional(readOnly = true)
    override fun findOfficeByMaxPrice(): MutableList<GetOfficeMaxByRes> {
        val groupingHistory = officeBackAccountRepository.findOfficeBankAccountByMaxPrice()
        val grouping = groupingHistory.groupBy { it.date }

        val returnList = mutableListOf<GetOfficeMaxByRes>()
        for (key in grouping.keys) {
            val dataList = grouping.get(key)!!.map {
                val officeInfo = officeRepository.findByIdAndActive(it.officeId.toLong(), true)
                GetOfficeMaxByDetailRes(sumAmt = it.sum.toLong(), brCode = officeInfo!!.code, brName = officeInfo.name)
            }.toMutableList()
            returnList.add(GetOfficeMaxByRes(year = key.toLong(), dataList = dataList))
        }
        return returnList
    }
}