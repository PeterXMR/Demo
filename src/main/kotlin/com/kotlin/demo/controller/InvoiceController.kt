package com.kotlin.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kotlin.demo.model.Invoice
import com.kotlin.demo.repository.InvoiceRepository
import com.kotlin.demo.service.InvoiceService
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/demo/api")
class InvoiceController(
    private val invoiceRepository: InvoiceRepository,
    private val invoiceService: InvoiceService
){

    @Transactional
    @PostMapping("/invoice")
    fun createInvoice(@RequestBody invoice: String): Any {
        val invoiceData = getRequestData(invoice) ?: return ResponseEntity.badRequest()
        if (invoiceService.paymentFormIsValid(invoiceData.payment_form)) {
            return ResponseEntity.ok(invoiceRepository.save(invoiceData))
        }
        return ResponseEntity.badRequest().body("Invalid payment_form: ${invoiceData.payment_form}")
    }

    @GetMapping("/invoice/{uuid}")
    fun getByUuid(@PathVariable uuid: String): Any {
        val selectedInvoice: Optional<Invoice> = invoiceRepository.findByUuid(uuid)
        if (selectedInvoice.isEmpty) {
            return ResponseEntity.status(404).body(getUuidNotFoundMessage(uuid))
        }
        return ResponseEntity.ok().body(invoiceRepository.findByUuid(uuid))
    }

    @Transactional
    @PatchMapping("/invoice/{uuid}")
    fun updateInvoice(@PathVariable uuid: String, @RequestBody invoice: String): Any {
        val invoiceData = getRequestData(invoice) ?: return ResponseEntity.badRequest()
        val selectedInvoice: Optional<Invoice> = invoiceRepository.findByUuid(uuid)
        if (selectedInvoice.isEmpty) {
            return ResponseEntity.status(404).body(getUuidNotFoundMessage(uuid))
        }
        invoiceData.uuid = selectedInvoice.get().uuid
        return ResponseEntity.ok(invoiceRepository.save(invoiceData))
    }

    @Transactional
    @DeleteMapping("/invoice/{uuid}")
    fun deleteByUuid(@PathVariable uuid: String): ResponseEntity<Optional<Invoice>> {
        return ResponseEntity.ok().body(invoiceRepository.deleteByUuid(uuid))
    }

    fun getRequestData(invoice: String): Invoice? {
        val objectMapper = ObjectMapper().registerModules(KotlinModule.Builder().build(), JavaTimeModule())
        return objectMapper.readValue(invoice, Invoice::class.java)
    }

    fun getUuidNotFoundMessage(uuid: String): String {
        return "Invoice with uuid: $uuid not found"
    }
}
