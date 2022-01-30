package com.ayanc.transactiontracker.configuration

import com.codahale.metrics.health.HealthCheck

class AppHealthCheck: HealthCheck() {
    override fun check(): Result {
        if(true){
            return Result.healthy();
        }
        return Result.unhealthy("Error message");
    }
}