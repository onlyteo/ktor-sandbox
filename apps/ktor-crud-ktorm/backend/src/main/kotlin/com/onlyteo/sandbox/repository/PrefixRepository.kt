package com.onlyteo.sandbox.repository

import com.onlyteo.sandbox.config.readCsvFile
import com.onlyteo.sandbox.model.Prefix
import kotlin.random.Random

class PrefixRepository(private val prefixes: List<Prefix>) {
    constructor(prefixesFile: String) : this(readCsvFile(prefixesFile))

    fun getPrefix(): Prefix {
        val index = Random.nextInt(prefixes.size)
        return prefixes[index]
    }
}