package com.onlyteo.sandbox.async.runner

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

open class CoroutineAsyncRunner(
    private val runFunction: (() -> Unit),
    private val abortFunction: (() -> Unit),
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val taskRef: AtomicReference<Job> = AtomicReference(Job())
) : AsyncRunner<CoroutineScope> {
    private val logger = LoggerFactory.getLogger(CoroutineAsyncRunner::class.java)

    constructor(
        taskFunction: (() -> Unit),
        successFunction: (() -> Unit) = {},
        errorFunction: ((Throwable) -> Unit) = {},
        abortFunction: (() -> Unit) = {},
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        taskRef: AtomicReference<Job> = AtomicReference(Job()),
        taskLatch: AtomicBoolean = AtomicBoolean(true)
    ) : this(
        runFunction = {
            while (taskLatch.get()) {
                try {
                    taskFunction()
                    successFunction()
                } catch (throwable: Throwable) {
                    errorFunction(throwable)
                }
            }
        },
        abortFunction = {
            taskLatch.set(false)
            abortFunction()
        },
        coroutineDispatcher = coroutineDispatcher,
        taskRef = taskRef
    )

    override fun run(context: CoroutineScope) {
        logger.info("Running coroutine async function")
        taskRef.set(context.launch(coroutineDispatcher) {
            runFunction()
        })
    }

    override fun abort() {
        logger.info("Aborting coroutine async function")
        taskRef.get().cancel()
        abortFunction()
    }
}