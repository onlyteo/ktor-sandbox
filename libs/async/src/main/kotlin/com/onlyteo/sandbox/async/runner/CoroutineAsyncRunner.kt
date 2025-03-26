package com.onlyteo.sandbox.async.runner

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

open class CoroutineAsyncRunner<T>(
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val keepRunning: AtomicBoolean = AtomicBoolean(true)
) : AsyncRunner<T> {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val jobRef: AtomicReference<Job> = AtomicReference(Job())

    override fun run(task: () -> T, onSuccess: (T) -> Unit, onFailure: (Throwable) -> Unit) {
        logger.info("Running coroutine async function")
        jobRef.set(coroutineScope.launch(coroutineDispatcher) {
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
        logger.info("Aborting coroutine async function")
        keepRunning.set(false)
        jobRef.get().cancel()
        onAbort()
    }
}