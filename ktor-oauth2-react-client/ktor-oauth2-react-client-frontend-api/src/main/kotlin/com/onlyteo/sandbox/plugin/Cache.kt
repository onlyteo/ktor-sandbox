package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.cache.InMemoryRequestCache
import com.onlyteo.sandbox.cache.RequestCache

val requestCacheSupplier: RequestCache<String, String> get() = InMemoryRequestCache()