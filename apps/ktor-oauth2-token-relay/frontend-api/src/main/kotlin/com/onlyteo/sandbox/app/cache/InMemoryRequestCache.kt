package com.onlyteo.sandbox.app.cache

class InMemoryRequestCache : RequestCache<String, String> {

    private val cache = mutableMapOf<String, String>()

    override fun get(key: String): String? {
        return cache[key]
    }

    override fun put(key: String, value: String): String? {
        return cache.put(key, value)
    }

    override fun remove(key: String): String? {
        return cache.remove(key)
    }
}