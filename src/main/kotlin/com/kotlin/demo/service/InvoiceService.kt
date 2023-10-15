package com.kotlin.demo.service

import com.kotlin.demo.Payment
import org.springframework.stereotype.Service


@Service
class InvoiceService () {

    fun paymentFormIsValid(paymentString: String): Boolean {
        return Payment.values().any { payment -> paymentString.contains(payment.type) }
    }
}