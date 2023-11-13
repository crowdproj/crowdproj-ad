package com.crowdproj.ad.app

import com.crowdproj.ad.app.plugins.replaceServers
import kotlin.test.Test
import kotlin.test.assertContains

class RegexTest {
    val yml = """
        openapi: 3.0.3
        info:
          title: 'Marketplace Ads service '
          description: 'Marketplace a place where sellers and buyers meat each other. Ads service provides ads handling from 
          both sellers and buyers'
          license:
            #    identifier: Apache-2.0
            name: Apache 2.0
            url: https://www.apache.org/licenses/LICENSE-2.0.html
          version: 1.0.0
        servers:
          - url: http://localhost:8080/ads
        tags:
          - name: ad
            description: Объявление (о покупке или продаже)
        paths:
          /v1/create:
            post:
              tags:
                - ad
              summary: Create ad

    """.trimIndent()
    val servers = listOf(
        "http://xxx:8090/app",
        "https://yyy:8090/app",
    )

    @Test
    fun test() {
        val res = yml.replaceServers(servers)

        assertContains(res, "http://xxx:8090/app")
        assertContains(res, Regex("^tags:\$", setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE)))
        println(res)
    }
}
