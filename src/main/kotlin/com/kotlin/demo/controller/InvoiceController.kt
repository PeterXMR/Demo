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
    @PutMapping("/invoice")
    fun createInvoice(@RequestBody invoice: String): Any {
        val mapper = ObjectMapper().registerModules(KotlinModule.Builder().build(),
            JavaTimeModule())
        val invoiceData = mapper.readValue(invoice, Invoice::class.java)
        if (invoiceService.paymentFormIsValid(invoiceData.payment_form)) {
            return ResponseEntity.ok(invoiceRepository.save(invoiceData))
        }
        return ResponseEntity.badRequest().body("Invalid payment_form: ${invoiceData.payment_form}")
    }

    @GetMapping("/invoice/{uuid}")
    fun getByUuid(@PathVariable uuid: String): ResponseEntity<Optional<Invoice>> {
        return ResponseEntity.ok().body(invoiceRepository.findByUuid(uuid))
    }

    @Transactional
    @PatchMapping("/invoice/{uuid}")
    fun updateInvoice(@PathVariable uuid: String, @RequestBody invoice: String): Any {
        val selectedInvoice: Optional<Invoice> = invoiceRepository.findByUuid(uuid)
        if (selectedInvoice.isEmpty()) {
            return ResponseEntity.notFound()
        }
        val mapper = ObjectMapper().registerModules(KotlinModule.Builder().build(), JavaTimeModule())
        val invoiceData = mapper.readValue(invoice, Invoice::class.java)
        invoiceData.id = selectedInvoice.get().id
        return ResponseEntity.ok(invoiceRepository.save(invoiceData))
    }

    @Transactional
    @DeleteMapping("/invoice/{uuid}")
    fun deleteByUuid(@PathVariable uuid: String): ResponseEntity<Optional<Invoice>> {
        return ResponseEntity.ok().body(invoiceRepository.deleteByUuid(uuid))
    }
}
