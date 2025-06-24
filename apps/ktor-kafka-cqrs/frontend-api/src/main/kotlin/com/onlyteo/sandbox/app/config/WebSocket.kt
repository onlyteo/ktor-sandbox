package com.onlyteo.sandbox.app.config

import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.channels.consumeEach

suspend fun WebSocketSession.consumeEach(action: (Frame) -> Unit): Unit = incoming.consumeEach(action)