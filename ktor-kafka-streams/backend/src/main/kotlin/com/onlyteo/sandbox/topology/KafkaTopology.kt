package com.onlyteo.sandbox.topology

import com.onlyteo.sandbox.config.buildJsonSerde
import com.onlyteo.sandbox.config.buildLogger
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.ValueJoiner

fun buildKafkaTopology(context: ApplicationContext): Topology = StreamsBuilder().apply {
    val properties = context.properties.kafka.streams
    val logger = buildLogger

    val personTable = table(properties.sourceTopic, Materialized.with(Serdes.String(), buildJsonSerde<Person>()))
    //.foreach { _, person -> logger.info("Received person \"${person.name}\" on Kafka topic \"${properties.sourceTopic}\"") }

    val greetingStream = stream(properties.targetTopic, Consumed.with(Serdes.String(), buildJsonSerde<Greeting>()))
    //.foreach { _, person -> logger.info("Received greeting \"${person.message}\" on Kafka topic \"${properties.targetTopic}\"") }

    val joiner = ValueJoiner<Greeting, Person, Pair<Person, Greeting>> { left, right -> right to left }

    greetingStream.leftJoin(personTable, joiner)
        .foreach { key, value -> logger.info("Received: {} -> {}", key, value) }
}.build()
