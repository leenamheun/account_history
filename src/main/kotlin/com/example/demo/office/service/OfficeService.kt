package com.example.demo.office.service

import com.example.demo.office.data.GetOfficeMaxByRes

interface OfficeService {
    fun findOfficeByMaxPrice(): MutableList<GetOfficeMaxByRes>
}