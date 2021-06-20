package com.example.demo.office.data

data class GetOfficeMaxByRes(
    val year: Long? = 0,
    val dataList: MutableList<GetOfficeMaxByDetailRes>
)

data class GetOfficeMaxByDetailRes(
    val brName: String? = null,
    val brCode: String? = null,
    val sumAmt: Long? = 0,
)