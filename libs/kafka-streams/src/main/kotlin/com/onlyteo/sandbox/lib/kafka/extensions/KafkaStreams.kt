package com.onlyteo.sandbox.lib.kafka.extensions

import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Named
import org.apache.kafka.streams.processor.PunctuationType
import org.apache.kafka.streams.processor.api.Processor
import org.apache.kafka.streams.processor.api.ProcessorContext
import org.apache.kafka.streams.processor.api.Record
import java.time.Duration
import java.time.Instant

fun <K, V> KStream<K, V>.ctxFilter(
    name: String,
    vararg stateStoreNames: String = emptyArray(),
    function: ProcessorContext<K, V>.(V) -> Boolean
): KStream<K, V> {
    val processor = {
        ContextProcessor<K, V, K, V> { record ->
            val result = function(record.value())
            if (result) forward(record)
        }
    }
    return process(processor, Named.`as`(name), *stateStoreNames)
}

fun <K, V1, V2> KStream<K, V1>.ctxMapValue(
    name: String,
    vararg stateStoreNames: String = emptyArray(),
    function: ProcessorContext<K, V2>.(V1) -> V2?
): KStream<K, V2> {
    val processor = {
        ContextProcessor<K, V1, K, V2> { record ->
            val result = function(record.value())
            forward(record.withValue(result))
        }
    }
    return process(processor, Named.`as`(name), *stateStoreNames)
}

fun <K, V1, V2> KStream<K, V1>.ctxMapValueNonNull(
    name: String,
    vararg stateStoreNames: String = emptyArray(),
    function: ProcessorContext<K, V2>.(V1) -> V2?
): KStream<K, V2> {
    val processor = {
        ContextProcessor<K, V1, K, V2> { record ->
            val result = function(record.value())
            if (result != null) forward(record.withValue(result))
        }
    }
    return process(processor, Named.`as`(name), *stateStoreNames)
}

fun <K1, V1, K2, V2> KStream<K1, V1>.ctxProcess(
    name: String,
    vararg stateStoreNames: String = emptyArray(),
    punctuation: Punctuation<K2, V2>? = null,
    function: ProcessorContext<K2, V2>.(Record<K1, V1>) -> Unit
): KStream<K2, V2> {
    val processor = {
        ContextProcessor(function = function, punctuation = punctuation)
    }
    return process(processor, Named.`as`(name), *stateStoreNames)
}

class ContextProcessor<K1, V1, K2, V2>(
    private val punctuation: Punctuation<K2, V2>? = null,
    private val function: ProcessorContext<K2, V2>.(Record<K1, V1>) -> Unit,
) : Processor<K1, V1, K2, V2> {
    private lateinit var context: ProcessorContext<K2, V2>

    override fun init(context: ProcessorContext<K2, V2>?) {
        super.init(context)
        this.context = requireNotNull(context) { "ProcessorContext must not be null during init" }
        if (punctuation != null) {
            context.schedule(punctuation.interval, punctuation.type) { timestamp ->
                punctuation.function(Instant.ofEpochMilli(timestamp), this.context)
            }
        }
    }

    override fun process(record: Record<K1, V1>?) {
        if (record == null) return
        with(context) { function(record) }
    }
}

data class Punctuation<K, V>(
    val interval: Duration,
    val type: PunctuationType,
    val function: (Instant, ProcessorContext<K, V>) -> Unit
)
