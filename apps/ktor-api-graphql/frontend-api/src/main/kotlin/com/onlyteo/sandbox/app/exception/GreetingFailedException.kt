package com.onlyteo.sandbox.app.exception

class GreetingFailedException(override val message: String) : RuntimeException(message)