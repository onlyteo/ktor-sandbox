package com.onlyteo.sandbox.app.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.thymeleaf.Thymeleaf
import org.thymeleaf.messageresolver.StandardMessageResolver
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

fun Application.configureThymeleaf() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
        setMessageResolver(StandardMessageResolver())
    }
}