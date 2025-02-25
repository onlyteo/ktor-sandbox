package com.onlyteo.sandbox.runner

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

open class ThreadPoolAsyncRunner(
    private val runFunction: (() -> Unit),
    private val abortFunction: (() -> Unit),
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor(),
    private val taskRef: AtomicReference<Future<*>> = AtomicReference(CompletableFuture<Nothing>())
) : AsyncRunner<Nothing> {
    constructor(
        runFunction: (() -> Unit),
        errorFunction: ((Throwable) -> Unit),
        abortFunction: (() -> Unit),
        executorService: ExecutorService = Executors.newSingleThreadExecutor(),
        taskRef: AtomicReference<Future<*>> = AtomicReference(CompletableFuture<Nothing>()),
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
        executorService = executorService,
        taskRef = taskRef
    )

    override fun run(context: Nothing) {
        taskRef.set(executorService.submit {
            runFunction()
        })
    }

    override fun abort() {
        taskRef.get().cancel(true)
        abortFunction()
    }
}