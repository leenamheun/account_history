package com.example.demo.office.repository.service

import com.example.demo.office.data.GetOfficeMaxByRes

interface OfficeService {
    fun findOfficeByMaxPrice(): MutableList<GetOfficeMaxByRes>
}