package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.cache.InMemoryRequestCache
import com.onlyteo.sandbox.cache.RequestCache

fun buildRequestCache(): RequestCache<String, String> = InMemoryRequestCache()