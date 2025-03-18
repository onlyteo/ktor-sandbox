package com.onlyteo.sandbox.async.runner

interface AsyncRunner<C> {
    fun run(context: C)
    fun abort()
}