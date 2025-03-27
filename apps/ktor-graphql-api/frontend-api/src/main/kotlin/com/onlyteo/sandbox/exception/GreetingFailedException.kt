package com.onlyteo.sandbox.exception

class GreetingFailedException(override val message: String) : RuntimeException(message)