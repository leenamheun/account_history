package com.example.demo.record

import com.example.demo.backaccount.entity.BankAccount
import com.example.demo.backaccount.repository.BankAccountRepository
import com.example.demo.history.entity.History
import com.example.demo.history.repository.HistoryRepository
import com.example.demo.office.entity.Office
import com.example.demo.office.entity.OfficeBankAccount
import com.example.demo.office.entity.OfficeStatus
import com.example.demo.office.entity.OfficeTransfer
import com.example.demo.office.repository.OfficeBankAccountRepository
import com.example.demo.office.repository.OfficeRepository
import com.example.demo.office.repository.OfficeTransferRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
internal class RecodeHandlerTest @Autowired constructor(
    private val officeRepository: OfficeRepository,
    private val accountRepository: BankAccountRepository,
    private val historyRepository: HistoryRepository,
    private val officeBankAccountRepository: OfficeBankAccountRepository,
    private val officeTransferRepository: OfficeTransferRepository
) {
    @Test
    fun initOffice() {
        try {
            var line: String?
            val fr = FileInputStream("src/main/resources/data/office.csv")
            val isr = InputStreamReader(fr, "UTF-8")
            val bis = BufferedReader(isr)
            var count = 0
            while (bis.readLine().also { line = it } != null) {
                val arry = line!!.trim().split(",")
                if ((++count) == 1) {
                    continue
                }
                val officeCode = arry[0].trim()
                val officeName = arry[1]

                val office = Office(name = officeName, code = officeCode)
                officeRepository.save(office)

            }
        } catch (ioException: Exception) {
            ioException.printStackTrace()
        }
    }

    @Test
    fun initBankAccount() {
        try {
            var line: String?
            val fr = FileInputStream("src/main/resources/data/account.csv")
            val isr = InputStreamReader(fr, "UTF-8")
            val bis = BufferedReader(isr)
            var count = 0;
            while (bis.readLine().also { line = it } != null) {
                if ((++count) == 1) {
                    continue
                }
                val arry = line!!.split(",")
                val accountNumbers = arry[0]
                val accountName = arry[1]
                val officeCode = arry[2]

                val office = officeRepository.findTopByCodeAndActive(officeCode, true)
                    ?: throw Exception()
                val account = BankAccount(name = accountName, numbers = accountNumbers)
                val savedBankAccount = accountRepository.save(account)

                val officeBankAccount = OfficeBankAccount(office = office, account = savedBankAccount)
                officeBankAccountRepository.save(officeBankAccount)
            }
        } catch (ioException: Exception) {
            ioException.printStackTrace()
        }
    }

    @Test
    fun initHistory() {
        try {
            var line: String?
            val fr = FileInputStream("src/main/resources/data/history.csv")
            val isr = InputStreamReader(fr, "UTF-8")
            val bis = BufferedReader(isr)
            var count = 0;
            while (bis.readLine().also { line = it } != null) {
                if ((++count) == 1) {
                    continue
                }
                val arry = line!!.split(",")
                val historyDate = arry[0]
                val accountNumbers = arry[1]
                val historyNo = arry[2]
                val price = arry[3]
                val fee = arry[4]
                val cancleType = arry[5]

                val account = accountRepository.findTopByNumbersAndActive(accountNumbers, true) ?: throw Exception()
                val history = History(
                    historyNo = historyNo.toLong(),
                    price = price.toLong(),
                    fee = fee.toLong(),
                    cancle = cancleType,
                    date = historyDate,
                    account = account
                )
                historyRepository.save(history)
            }
        } catch (ioException: Exception) {
            ioException.printStackTrace()
        }
    }

    @Test
    fun transferOffice() {
        val source = officeRepository.findTopByCodeAndActive("B", true)
            ?: throw Exception()
        val target = officeRepository.findTopByCodeAndActive("A", true)
            ?: throw Exception()

        val transfer = OfficeTransfer(officeSource = source, officeTarget = target)
        officeTransferRepository.save(transfer)
        source.status = OfficeStatus.TRANSFERRING
        source.active = false
        officeRepository.save(source)
    }
}