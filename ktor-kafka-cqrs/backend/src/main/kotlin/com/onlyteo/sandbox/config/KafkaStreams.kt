package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import com.onlyteo.sandbox.model.toGreeting
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Named
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.processor.PunctuationType
import org.apache.kafka.streams.processor.Punctuator
import org.apache.kafka.streams.processor.api.Processor
import org.apache.kafka.streams.processor.api.ProcessorContext
import org.apache.kafka.streams.processor.api.ProcessorSupplier
import org.apache.kafka.streams.processor.api.Record
import org.apache.kafka.streams.state.Stores
import org.apache.kafka.streams.state.TimestampedKeyValueStore
import org.apache.kafka.streams.state.ValueAndTimestamp
import java.time.Duration
import java.time.Instant

context(ApplicationContext)
fun buildKafkaStreamsTopology(): Topology = StreamsBuilder().apply {

    val properties = properties.kafka.streams
    val logger = buildLogger

    addStateStore(
        Stores.timestampedKeyValueStoreBuilder(
            Stores.inMemoryKeyValueStore(properties.stateStore),
            Serdes.String(),
            buildJsonSerde<Person>()
        )
    )

    stream(properties.sourceTopic, Consumed.with(Serdes.String(), buildJsonSerde<Person>()))
        .peek { _, person -> logger.info("Received person \"${person.name}\" on Kafka topic \"${properties.sourceTopic}\"") }
        .process(buildPersonProcessorSupplier(), Named.`as`(properties.processor), properties.stateStore)
        .mapValues { person -> person.toGreeting() }
        .peek { _, greeting -> logger.info("Sending greeting \"${greeting.message}\" to Kafka topic \"${properties.targetTopic}\"") }
        .to(properties.targetTopic, Produced.with(Serdes.String(), buildJsonSerde<Greeting>()))
}.build()

private fun buildPersonPunctuator(
    processorContext: ProcessorContext<String, Person>,
    keyValueStore: TimestampedKeyValueStore<String, Person>
) = object : Punctuator {

    private val logger = buildLogger

    override fun punctuate(timestamp: Long) {
        val keyValueIterator = keyValueStore.all()
        while (keyValueIterator.hasNext()) {
            keyValueIterator.next().apply {
                val greetingTime = Instant.ofEpochMilli(value.timestamp())
                if (Instant.now().minusSeconds(60).isAfter(greetingTime)) {
                    val person = value.value()
                    logger.info("Completed delayed processing for \"{}\"", person.name)
                    keyValueStore.delete(person.name)
                    processorContext.forward(Record(person.name, person, Instant.now().toEpochMilli()))
                }
            }
        }
    }
}

context(ApplicationContext)
private fun buildPersonProcessorSupplier() = ProcessorSupplier { buildPersonProcessor() }

context(ApplicationContext)
private fun buildPersonProcessor() = object : Processor<String, Person, String, Person> {

    private val logger = buildLogger
    private lateinit var processorContext: ProcessorContext<String, Person>
    private lateinit var keyValueStore: TimestampedKeyValueStore<String, Person>

    override fun init(processorContext: ProcessorContext<String, Person>?) {
        val streamsProperties = properties.kafka.streams
        this.processorContext = checkNotNull(processorContext) { "ProcessorContext cannot be null" }
        this.keyValueStore = checkNotNull(processorContext.getStateStore(streamsProperties.stateStore)) {
            "No StateStore named ${streamsProperties.stateStore} found"
        }
        processorContext.schedule(
            Duration.ofSeconds(10),
            PunctuationType.WALL_CLOCK_TIME,
            buildPersonPunctuator(processorContext, keyValueStore)
        )
    }

    override fun process(record: Record<String, Person>?) {
        val person = record?.value() ?: return
        if (setOf("John", "Julie", "James", "Jenny").contains(person.name)) {
            // Delay greeting for these names by 1 minute
            logger.info("Delaying processing for \"{}\"", person.name)
            this.keyValueStore.put(person.name, ValueAndTimestamp.make(person, Instant.now().toEpochMilli()))
        } else {
            // Otherwise forward greeting
            logger.info("Completed processing for \"{}\"", person.name)
            this.processorContext.forward(record)
        }
    }
}

class KafkaStreamsExceptionHandler : StreamsUncaughtExceptionHandler {

    private val logger = buildLogger

    override fun handle(throwable: Throwable): StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse {
        logger.error("Uncaught Kafka Streams exception", throwable)
        return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.SHUTDOWN_APPLICATION
    }
}
