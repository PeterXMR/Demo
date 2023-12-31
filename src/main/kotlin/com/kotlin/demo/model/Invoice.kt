package com.kotlin.demo.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "invoices")
class Invoice(
    @Id
    var uuid: String,
    var created_at: ZonedDateTime,
    var updated_at: ZonedDateTime?,
    var amount: Long,
    var supplier_name: String,
    var supplier_id: Long,
    var customer_name: String,
    var customer_id: Long,
    var issuing_date: ZonedDateTime,
    var due_date: ZonedDateTime,
    var fulfilment_date: ZonedDateTime,
    var payment_form: String
)
