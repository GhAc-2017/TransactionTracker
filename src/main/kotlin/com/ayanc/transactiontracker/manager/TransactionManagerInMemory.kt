package com.ayanc.transactiontracker.manager

import com.ayanc.transactiontracker.models.Transaction
import com.ayanc.transactiontracker.models.TransactionDetails
import com.ayanc.transactiontracker.models.TransactionSum
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NTree(var transaction: Transaction, var list: MutableList<NTree?>) {
    var children: MutableList<NTree?> = list
}

object TransactionManagerInMemory {
    val transactionList: ArrayList<Transaction> = ArrayList()
    val transactionMaps: HashMap<String, NTree> = HashMap()
    var node: NTree = NTree(Transaction(amount = 0.0, type = "DO_NOT_USE", parentId = null), mutableListOf())

    fun getTransactionById(id: String): Transaction? {
        val transction = transactionMaps[id]?.transaction
        return transction
    }

    fun getAllTransaction(): Collection<Transaction>? {
        return transactionList
    }

    fun getAllTransactionByType(type: String): Collection<Transaction>? {
        return transactionList.filter { it.type.lowercase() == type.lowercase() }
    }

    fun addTransaction(transactionDetails: TransactionDetails): Transaction {
        val transaction = Transaction(
            amount = transactionDetails.amount,
            type = transactionDetails.type, parentId = transactionDetails.parentId
        )
        transactionList.add(transaction)
        if (!transactionDetails.parentId.isNullOrEmpty()) {
            //we can tell for sure that a transaction with parent value exists!
            if (transactionMaps.containsKey(transactionDetails.parentId)) {
                val addnode = transactionMaps[transactionDetails.parentId]
                val ntree = NTree(
                    transaction = transaction,
                    list = mutableListOf()
                )
                addnode?.children?.add(ntree)
                transactionMaps.putIfAbsent(transaction.id, ntree)
            }
        } else {
            val ntree = NTree(transaction = transaction,
                list = mutableListOf()
            )
            node.children.add(
                ntree
            )
            transactionMaps.putIfAbsent(transaction.id, ntree)
        }
        return transaction
    }

    fun getTransactionSum(transactionId: String): TransactionSum {
        val ntree = transactionMaps[transactionId] ?: error("Invalid transaction id")

        val q: Queue<NTree> = LinkedList<NTree>()
        q.add(ntree)
        val transactionval = mutableListOf<Double?>()
        transactionval.add(ntree.transaction.amount)
        while (!q.isEmpty()) {
            val front = q.poll()
            front.children.map {
                transactionval.add(it?.transaction?.amount)
                q.add(it)
            }
        }

        return TransactionSum(sum = DecimalFormat("#.##").format(transactionval.filterNotNull().sum()))
    }

    fun updateTransaction(transactionId: String, transactionDetails: TransactionDetails): Transaction {
        val ntree = transactionMaps[transactionId] ?: error("Invalid transaction id")
        val transaction = transactionList.find{ it.id == transactionId } ?: error("Transaction Not Recorded Previously")
        transactionList.remove(transaction)
        transactionList.add(transaction.copy(
            amount = transactionDetails.amount,
            type = transactionDetails.type,
            parentId = transactionDetails.parentId
        ))

        //de-link from previous parent
        //case 1: parent id null
        if (ntree.transaction.parentId == null) {
            node.children.remove(node.children.find { it == ntree })
        } else { //case 2: parent is there
            val prevParent = transactionMaps[ntree.transaction.parentId] ?: error("No Relevant Parent Found")
            prevParent.children.remove(prevParent.children.find { it == ntree })
        }

        ntree.transaction = ntree.transaction.copy(
            amount = transactionDetails.amount,
            type = transactionDetails.type,
            parentId = transactionDetails.parentId
        )

        //link to new parent
        if (transactionDetails.parentId == null) {
            node.children.add(ntree)
        } else {
            val newParent = transactionMaps[transactionDetails.parentId] ?: error("Please Enter a valid new parent id")
            newParent.children.add(ntree)
        }
        return transactionList.last()
    }
}
