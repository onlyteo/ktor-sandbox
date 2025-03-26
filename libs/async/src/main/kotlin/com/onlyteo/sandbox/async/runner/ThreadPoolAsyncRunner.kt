package com.onlyteo.sandbox.async.runner

import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

open class ThreadPoolAsyncRunner<T>(
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor(),
    private val keepRunning: AtomicBoolean = AtomicBoolean(true),
    private val meyInterruptIfRunning: AtomicBoolean = AtomicBoolean(true)
) : AsyncRunner<T> {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val futureRef: AtomicReference<Future<*>> = AtomicReference(CompletableFuture<Nothing>())

    override fun run(task: () -> T, onSuccess: (T) -> Unit, onFailure: (Throwable) -> Unit) {
        logger.info("Running thread pool async function")
        futureRef.set(executorService.submit {
            while (keepRunning.get()) {
                try {
                    val result = task()
                    onSuccess(result)
                } catch (throwable: Throwable) {
                    onFailure(throwable)
                }
            }
        })
    }

    override fun abort(onAbort: () -> Unit) {
        logger.info("Aborting thread pool async function")
        keepRunning.set(false)
        futureRef.get().cancel(meyInterruptIfRunning.get())
        onAbort()
    }
}