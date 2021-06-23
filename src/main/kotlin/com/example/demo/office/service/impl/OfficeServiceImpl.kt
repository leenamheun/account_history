package com.example.demo.office.service.impl

import com.example.demo.exception.OfficeNotFoundException
import com.example.demo.office.data.GetOfficeMaxByDetailRes
import com.example.demo.office.data.GetOfficeMaxByRes
import com.example.demo.office.data.GetOfficeSumByRes
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
            val contentList = mutableListOf<GetOfficeMaxByDetailRes>()
            grouping.get(key)!!.forEach { group ->
                val officeInfo = officeRepository.findTopById(group.officeId.toLong())?.let {
                    GetOfficeMaxByDetailRes(sumAmt = group.sum.toLong(), brCode = it.code, brName = it.name)
                } ?: return@forEach
                contentList.add(officeInfo)
            }
            returnList.add(GetOfficeMaxByRes(year = key.toLong(), dataList = contentList))
        }
        return returnList
    }

    @Transactional(readOnly = true)
    override fun findOfficeBySumPrice(name: String): GetOfficeSumByRes {
        val officeInfo = officeRepository.findByNameAndActive(name, true)
            ?: throw OfficeNotFoundException("br code not found error")
        val sum = officeRepository.findOfficeByTransfer(officeInfo.id!!)?.run { sum.toLong() }
        return GetOfficeSumByRes(sum = sum, brCode = officeInfo.code, brName = officeInfo.name)
    }
}