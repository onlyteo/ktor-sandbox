package com.onlyteo.sandbox.service

import com.onlyteo.sandbox.config.readCsvFile
import com.onlyteo.sandbox.model.Prefix
import kotlin.random.Random

class PrefixService(private val prefixes: List<Prefix>) {
    constructor(prefixesFile: String) : this(readCsvFile(prefixesFile))

    fun getPrefix(): Prefix {
        val index = Random.nextInt(prefixes.size)
        return prefixes[index]
    }
}