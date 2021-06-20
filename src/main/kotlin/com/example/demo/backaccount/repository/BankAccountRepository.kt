package com.example.demo.backaccount.repository

import com.example.demo.backaccount.entity.BankAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BankAccountRepository : JpaRepository<BankAccount, Long> {
    fun findTopByNumbersAndActive(accountNumbers: String, active: Boolean): BankAccount?

    @Query(
        "select ba from BankAccount ba " +
            "where not exists( select 1 from History h where h.account.id = ba.id and SUBSTRING(h.date,1,4) = :year )" +
            "and ba.active=true"
    )
    fun findByYearAndActive(@Param("year") year: String): List<BankAccount>

    fun findByIdAndActive(id: Long, active: Boolean): BankAccount
}

