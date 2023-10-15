package com.kotlin.demo

enum class Payment(val type: String) {
    BANK_TRANSFER("bank_transfer"),
    CASH("cash"),
    CREDIT_CARD("credit_card");
}