package com.ayanc.transactiontracker.configuration

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import javax.validation.constraints.NotNull


class TransactionTrackerConfig: Configuration() {

    @NotNull
    private val dataSourceFactory: @Valid DataSourceFactory? = DataSourceFactory()

    @JsonProperty("database")
    fun getDataSourceFactory(): DataSourceFactory? {
        return dataSourceFactory
    }
}