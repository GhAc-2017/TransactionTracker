package com.ayanc.transactiontracker.dao

import com.ayanc.transactiontracker.models.Transaction
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet
import java.sql.SQLException


    @RegisterMapper(TransactionMapper::class)
    interface TransactionDao {
        companion object {
            private const val TABLE_NAME = "transactions"
            private const val INSERT_INTO = "INSERT into $TABLE_NAME"
        }
        @SqlQuery("select * from $TABLE_NAME where id = :id")
        fun getTransaction(@Bind("id") id: String): Transaction?
    }



class TransactionMapper : ResultSetMapper<Transaction> {
    @Throws(SQLException::class)
    override fun map(i: Int, resultSet: ResultSet, statementContext: StatementContext): Transaction {
        return Transaction(resultSet.getString(ID), resultSet.getDouble(AMOUNT), resultSet.getString(TYPE), resultSet.getString(
            PARENTID))
    }

    companion object {
        private const val ID = "id"
        private const val AMOUNT = "amount"
        private const val TYPE = "type"
        private const val PARENTID = "parentId"
    }
}
