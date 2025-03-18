package com.onlyteo.sandbox.async.runner

import io.ktor.server.application.Application
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

open class CoroutineAsyncRunner(
    private val runFunction: (() -> Unit),
    private val abortFunction: (() -> Unit),
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val taskRef: AtomicReference<Job> = AtomicReference(Job())
) : AsyncRunner<Application> {
    constructor(
        taskFunction: (() -> Unit),
        successFunction: (() -> Unit) = {},
        errorFunction: ((Throwable) -> Unit),
        abortFunction: (() -> Unit),
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

    override fun run(context: Application) {
        taskRef.set(context.launch(coroutineDispatcher) {
            runFunction()
        })
    }

    override fun abort() {
        taskRef.get().cancel()
        abortFunction()
    }
}