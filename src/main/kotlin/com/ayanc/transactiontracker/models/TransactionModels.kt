package com.ayanc.transactiontracker.models

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val amount: Double,
    val type: String,
    val parentId: String?
)

data class TransactionDetails(
    @JsonProperty("amount")
    val amount: Double,
    @JsonProperty("type")
    val type: String,
    @JsonProperty("parentId")
    val parentId: String? = null
)

data class TransactionSum(
    val sum: String
)

