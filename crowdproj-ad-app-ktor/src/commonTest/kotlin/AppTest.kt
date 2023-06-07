package com.crowdproj.ad.app

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AppTest {

    @Test
    fun root() = testApplication {
        application { module() }
        val client = createClient {

        }

        val res = client.get("/").call
        val body = res.body<String>()

        assertContains(body, "CrowdProj Ads")
    }

}
