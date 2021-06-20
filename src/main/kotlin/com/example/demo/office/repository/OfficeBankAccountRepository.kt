package com.example.demo.office.repository

import com.example.demo.office.entity.OfficeBankAccount
import com.example.demo.office.repository.`interface`.GetOfficeBankAccountByMax
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OfficeBankAccountRepository : JpaRepository<OfficeBankAccount, Long> {
    @Query(
        "   select substr(h.date,1,4) as date, obc.office_id as officeId, sum(h.price) as sum\n" +
            "    from history h , office_bank_account obc\n" +
            "    where h.bank_account_id = obc.bank_account_id\n" +
            "    group by substr(h.date,1,4), obc.office_id\n", nativeQuery = true
    )
    fun findOfficeBankAccountByMaxPrice(): List<GetOfficeBankAccountByMax>
}