package com.ayanc.transactiontracker

import com.ayanc.transactiontracker.configuration.AppHealthCheck
import com.ayanc.transactiontracker.configuration.TransactionTrackerConfig
import com.ayanc.transactiontracker.manager.TransactionManager
import com.ayanc.transactiontracker.models.Transaction
import com.ayanc.transactiontracker.service.TransactionResource
import io.dropwizard.Application
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.hibernate.HibernateBundle
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI


class TransactionTrackerApplication: Application<TransactionTrackerConfig>() {
    companion object {
        @JvmStatic fun main(args: Array<String>) = TransactionTrackerApplication().run(*args)
    }


    override fun run(transactionTrackerConfig: TransactionTrackerConfig?, environment: Environment?) {

        // Datasource configuration
        val dataSource = transactionTrackerConfig?.getDataSourceFactory()?.build(environment!!.metrics(), "sql")
        val dbi = DBI(dataSource)


        environment?.jersey()?.register(TransactionResource(dbi.onDemand(TransactionManager::class.java)))
        environment?.healthChecks()?.register("APIHealthCheck", AppHealthCheck())
    }
}