package com.example.demo.office.service

import com.example.demo.office.data.GetOfficeMaxByRes
import com.example.demo.office.data.GetOfficeSumByRes

interface OfficeService {
    fun findOfficeByMaxPrice(): MutableList<GetOfficeMaxByRes>
    fun findOfficeBySumPrice(name: String): GetOfficeSumByRes
}