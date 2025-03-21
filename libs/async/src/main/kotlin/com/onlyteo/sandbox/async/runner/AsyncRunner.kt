package com.onlyteo.sandbox.async.runner

interface AsyncRunner<T> {
    fun run(context: T)
    fun abort()
}