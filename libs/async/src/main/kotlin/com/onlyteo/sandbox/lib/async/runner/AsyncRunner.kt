package com.onlyteo.sandbox.lib.async.runner

interface AsyncRunner<T> {
    fun run(task: () -> T, onSuccess: (T) -> Unit = {}, onFailure: (Throwable) -> Unit = {})
    fun abort(onAbort: () -> Unit = {})
}