package com.ayanc.transactiontracker.manager

import com.ayanc.transactiontracker.dao.TransactionDao
import com.ayanc.transactiontracker.models.Transaction
import org.skife.jdbi.v2.sqlobject.CreateSqlObject


abstract class TransactionManager {
    @CreateSqlObject
    abstract fun transactionDao(): TransactionDao


//    fun getTransactionById(id: String): Transaction? {
//        return Transaction("trn123", 1003.45, "CAR", null)
//        return transactionDao().getTransaction(id = id)
//    }
}

