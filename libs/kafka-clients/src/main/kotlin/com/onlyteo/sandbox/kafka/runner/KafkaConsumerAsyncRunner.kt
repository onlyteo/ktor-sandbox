package com.onlyteo.sandbox.kafka.runner

interface KafkaConsumerAsyncRunner {
    fun init()
    fun start()
    fun stop()
}