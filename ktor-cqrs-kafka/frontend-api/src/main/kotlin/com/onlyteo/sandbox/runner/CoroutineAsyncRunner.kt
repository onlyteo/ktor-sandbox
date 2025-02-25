package com.onlyteo.sandbox.runner

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
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
) : AsyncRunner<CoroutineScope> {
    constructor(
        runFunction: (() -> Unit),
        errorFunction: ((Throwable) -> Unit),
        abortFunction: (() -> Unit),
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        taskRef: AtomicReference<Job> = AtomicReference(Job()),
        taskLatch: AtomicBoolean = AtomicBoolean(true)
    ) : this(
        runFunction = {
            while (taskLatch.get()) {
                try {
                    runFunction()
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
        taskRef.set(context.launch(coroutineDispatcher) {
            runFunction()
        })
    }

    override fun abort() {
        taskRef.get().cancel()
        abortFunction()
    }
}