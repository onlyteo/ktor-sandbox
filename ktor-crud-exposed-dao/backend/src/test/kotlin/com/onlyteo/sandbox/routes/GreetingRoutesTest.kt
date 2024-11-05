package com.onlyteo.sandbox.routes

import com.onlyteo.sandbox.context.TestContext
import com.onlyteo.sandbox.model.Greeting
import com.onlyteo.sandbox.model.Person
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldEndWith
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication

class GreetingRoutesTest : FunSpec({
    with(TestContext()) {

        test("Should get 400 Bad Request using GET request when missing 'name' query parameter") {
            testApplication {
                configureServer()
                val httpClient = createClient { configureClient() }

                val response = httpClient.get("/api/greetings")

                response.status shouldBe HttpStatusCode.BadRequest
            }
        }

        test("Should POST greeting then get greeting using GET request for Jack") {
            testApplication {
                configureServer()
                val httpClient = createClient { configureClient() }

                val postResponse = httpClient.post("/api/greetings") {
                    contentType(ContentType.Application.Json)
                    setBody(Person("Jack"))
                }

                val postBody = postResponse.body<Greeting>()
                postResponse.status shouldBe HttpStatusCode.OK
                postBody.message shouldEndWith "Jack!"

                val getResponse = httpClient.get("/api/greetings") {
                    url { parameters.append("name", "Jack") }
                }

                val getBody = getResponse.body<List<Greeting>>()
                getResponse.status shouldBe HttpStatusCode.OK
                getBody.size shouldBe 1
                getBody[0] shouldBe postBody
            }
        }
    }
})