package com.ayanc.transactiontracker.service

import com.ayanc.transactiontracker.manager.TransactionManager
import com.ayanc.transactiontracker.manager.TransactionManagerInMemory
import com.ayanc.transactiontracker.models.Transaction
import com.ayanc.transactiontracker.models.TransactionDetails
import com.ayanc.transactiontracker.models.TransactionSum
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/transactionservice")
@Produces(MediaType.APPLICATION_JSON)
class TransactionResource(val transactionManager: TransactionManager) {


    @POST
    @Path("/transaction/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun addTransaction(
        transactionUpdate: TransactionDetails
    ): Transaction{
        return TransactionManagerInMemory.addTransaction(transactionDetails = transactionUpdate)
    }

    @PUT
    @Path("/transaction/{transaction-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun updateTransaction(
        @PathParam("transaction-id") transactionId: String,
        transactionUpdate: TransactionDetails
    ): Transaction {
        return TransactionManagerInMemory.updateTransaction(
            transactionId = transactionId,
            transactionDetails = transactionUpdate
        )
    }

    @GET
    @Path("/transaction/{transaction-id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTransaction(
        @PathParam("transaction-id") transactionId: String
    ): Transaction? {
        return TransactionManagerInMemory.getTransactionById(id = transactionId)
    }

    @GET
    @Path("/transactions")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllTransaction(
    ): Collection<Transaction>? {
        return TransactionManagerInMemory.getAllTransaction()
    }

    @GET
    @Path("/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTransactionByType(
        @PathParam("type") type: String
    ): Collection<Transaction>? {
        return TransactionManagerInMemory.getAllTransactionByType(type = type)
    }

    @GET
    @Path("/sum/{transaction-id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTransactionSum(
        @PathParam("transaction-id") transactionId: String
    ): TransactionSum {
        return TransactionManagerInMemory.getTransactionSum(transactionId = transactionId)
    }
}