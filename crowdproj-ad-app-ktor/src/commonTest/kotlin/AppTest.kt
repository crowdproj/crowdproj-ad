package com.crowdproj.ad.app

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertContains

class AppTest {

    @Test
    fun root() = testApplication {
        application { module() }
        val client = createClient {

        }

        val res = client.get("/").call
        val body = res.body<String>()

        assertContains(body, "working")
    }

}
