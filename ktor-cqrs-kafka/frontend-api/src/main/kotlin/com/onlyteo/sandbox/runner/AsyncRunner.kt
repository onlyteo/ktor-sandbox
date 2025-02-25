package com.onlyteo.sandbox.runner

interface AsyncRunner<C> {
    fun run(context: C)
    fun abort()
}