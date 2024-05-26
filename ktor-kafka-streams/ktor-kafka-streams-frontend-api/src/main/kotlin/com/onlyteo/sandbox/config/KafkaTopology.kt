package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed

context(ApplicationContext)
fun buildStreamsTopology(): Topology = StreamsBuilder().apply {
    val properties = applicationProperties.kafka.streams
    stream(properties.sourceTopic, Consumed.with(Serdes.String(), buildJsonSerde<Greeting>()))
        .foreach { _, greeting ->
            logger.info(greeting.message)
        }
}.build()
