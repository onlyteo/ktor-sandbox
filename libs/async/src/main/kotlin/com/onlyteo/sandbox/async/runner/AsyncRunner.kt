package com.onlyteo.sandbox.async.runner

interface AsyncRunner<T> {
    fun run(task: () -> T, onSuccess: (T) -> Unit = {}, onFailure: (Throwable) -> Unit = {})
    fun abort(onAbort: () -> Unit = {})
}