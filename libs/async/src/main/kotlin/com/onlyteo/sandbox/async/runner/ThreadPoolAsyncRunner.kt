package com.onlyteo.sandbox.async.runner

import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

open class ThreadPoolAsyncRunner<T>(
    private val runFunction: (() -> Unit),
    private val abortFunction: (() -> Unit),
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor(),
    private val taskRef: AtomicReference<Future<*>> = AtomicReference(CompletableFuture<Nothing>())
) : AsyncRunner<T> {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    constructor(
        taskFunction: (() -> Unit),
        successFunction: (() -> Unit) = {},
        errorFunction: ((Throwable) -> Unit) = {},
        abortFunction: (() -> Unit) = {},
        executorService: ExecutorService = Executors.newSingleThreadExecutor(),
        taskRef: AtomicReference<Future<*>> = AtomicReference(CompletableFuture<Nothing>()),
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
        executorService = executorService,
        taskRef = taskRef
    )

    override fun run(context: T) {
        logger.info("Running thread pool async function")
        taskRef.set(executorService.submit {
            runFunction()
        })
    }

    override fun abort() {
        logger.info("Aborting thread pool async function")
        taskRef.get().cancel(true)
        abortFunction()
    }
}