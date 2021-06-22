package com.example.demo.history.repository


import com.example.demo.backaccount.entity.BankAccount
import com.example.demo.history.entity.History
import com.example.demo.history.repository.`interface`.GetGroupBySumData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface HistoryRepository : JpaRepository<History, Long> {
    fun findAllByActive(active: Boolean): List<History>

    @Query(
        "select substr(date, 1, 4) as date,\n" +
            "       bank_account_id as accountId,\n" +
            "       sum(\n" +
            "               case\n" +
            "                   when cancle = 'N' then price - fee\n" +
            "                   when cancle = 'Y' then (price - fee) * -1\n" +
            "                   end\n" +
            "           ) as sum\n" +
            "from history\n" +
            "group by substr(date, 1, 4), bank_account_id", nativeQuery = true
    )
    fun findGroupBySum(): List<GetGroupBySumData>

    @Query("select h from History h where h.active = true and h.account.id in (:account)")
    fun findByBankAccountId(@Param("account") account: MutableList<Long>): MutableList<History>
}
