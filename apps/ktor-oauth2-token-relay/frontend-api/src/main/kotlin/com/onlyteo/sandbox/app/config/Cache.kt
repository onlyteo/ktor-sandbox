package com.onlyteo.sandbox.app.config

import com.onlyteo.sandbox.app.cache.InMemoryRequestCache
import com.onlyteo.sandbox.app.cache.RequestCache

fun buildRequestCache(): RequestCache<String, String> = InMemoryRequestCache()