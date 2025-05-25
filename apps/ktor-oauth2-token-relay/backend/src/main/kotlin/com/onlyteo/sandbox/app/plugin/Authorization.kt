package com.onlyteo.sandbox.app.plugin

import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.server.application.log
import io.ktor.server.auth.AuthenticationChecked
import io.ktor.server.auth.principal
import io.ktor.server.routing.Route
import io.ktor.server.routing.RouteSelector
import io.ktor.server.routing.RouteSelectorEvaluation
import io.ktor.server.routing.RoutingContext
import io.ktor.server.routing.RoutingResolveContext

val Authorization = createRouteScopedPlugin("Authorization") {
    on(AuthenticationChecked) { call ->
        val any = call.principal<Any>()
        application.log.info("Authenticated: $any")
    }
}

class AuthorizedRouteSelector : RouteSelector() {
    override suspend fun evaluate(
        context: RoutingResolveContext,
        segmentIndex: Int
    ): RouteSelectorEvaluation {
        return RouteSelectorEvaluation.Constant
    }

    override fun toString(): String = "(authorize)"
}

fun Route.authorize(
    build: Route.() -> Unit
): Route {
    val authorizedRoute = createChild(AuthorizedRouteSelector())
    authorizedRoute.install(Authorization)
    authorizedRoute.build()
    return authorizedRoute
}

suspend fun RoutingContext.authorize(
    build: suspend RoutingContext.() -> Unit
): RoutingContext {
    build()
    return this
}