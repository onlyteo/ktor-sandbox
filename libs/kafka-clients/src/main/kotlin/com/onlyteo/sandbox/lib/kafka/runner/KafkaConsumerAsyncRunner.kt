package com.onlyteo.sandbox.lib.kafka.runner

interface KafkaConsumerAsyncRunner {
    fun init()
    fun start()
    fun stop()
}