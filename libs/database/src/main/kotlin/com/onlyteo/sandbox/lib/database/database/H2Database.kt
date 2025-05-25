package com.onlyteo.sandbox.lib.database.database

import org.h2.tools.Server

class H2Database(private val webServer: Server) {
    constructor(port: Int) : this(Server.createWebServer("-webPort", "$port", "-tcpAllowOthers"))

    fun start() {
        webServer.start()
    }

    fun stop() {
        webServer.stop()
    }
}