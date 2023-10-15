package com.kotlin.demo.repository

import com.kotlin.demo.model.Invoice
import org.springframework.data.repository.CrudRepository
import java.util.*

interface InvoiceRepository : CrudRepository<Invoice, Long> {
    fun findByUuid(uuid: String): Optional<Invoice>
    fun deleteByUuid(uuid: String): Optional<Invoice>
}