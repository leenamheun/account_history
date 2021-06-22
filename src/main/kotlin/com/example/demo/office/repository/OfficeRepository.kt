package com.example.demo.office.repository

import com.example.demo.office.entity.Office
import com.example.demo.office.repository.`interface`.OfficeTransferSum
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OfficeRepository : JpaRepository<Office, Long> {
    fun findTopByCodeAndActive(code: String, active: Boolean): Office?
    fun findByIdAndActive(id: Long, active: Boolean): Office?

    @Query("select obc.office_id as officeId,\n" +
        "       sum(\n" +
        "               case\n" +
        "                   when h.cancle = 'N' then (price - fee)\n" +
        "                   when h.cancle = 'Y' then (price - fee) * -1\n" +
        "                   end\n" +
        "       ) as sum\n" +
        "from\n" +
        "    (\n" +
        "        select id\n" +
        "        from office\n" +
        "        where name = ?1\n" +
        "        and active = true\n" +
        "        union\n" +
        "        select office_source_id as id\n" +
        "        from office_transfer ot\n" +
        "        where exists(select 1 from office o where o.id = ot.office_target_id and  o.name = ?1)\n" +
        "    ) ot, office_bank_account obc, history h\n" +
        "where ot.id = obc.office_id\n" +
        "and obc.bank_account_id = h.bank_account_id\n" +
        "group by ot.id", nativeQuery = true)
    fun findOfficeByTransfer(officeName: String): List<OfficeTransferSum>
}