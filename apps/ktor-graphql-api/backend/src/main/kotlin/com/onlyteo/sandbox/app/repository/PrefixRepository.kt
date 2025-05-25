package com.onlyteo.sandbox.app.repository

import com.onlyteo.sandbox.lib.files.reader.readCsvFile
import com.onlyteo.sandbox.app.model.Prefix
import kotlin.random.Random

class PrefixRepository(private val prefixes: List<Prefix>) {
    constructor(prefixesFile: String) : this(readCsvFile(prefixesFile))

    fun getPrefix(): Prefix {
        val index = Random.nextInt(prefixes.size)
        return prefixes[index]
    }
}