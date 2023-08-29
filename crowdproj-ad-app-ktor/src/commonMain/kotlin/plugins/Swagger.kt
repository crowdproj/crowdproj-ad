package com.crowdproj.ad.app.plugins

import com.crowdproj.ad.app.configs.CwpAdAppSettings
import com.crowdproj.ad.app.resources.RESOURCES
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.charsets.*
import kotlin.text.*
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

private const val SWAGGER_VERSION = "4.18.3"
private const val SWAGGER_URL = "https://cdnjs.cloudflare.com/ajax/libs/swagger-ui/$SWAGGER_VERSION"
private val SWAGGER_HTML = """
    <!DOCTYPE html>
    <html lang="en">
    <head>
    	<meta charset="UTF-8">
    	<title>CrowdProj Ads </title>
    	<script src="$SWAGGER_URL/swagger-ui-standalone-preset.min.js" integrity="sha512-dQDXlqaJDnXhlgBLVyoNPZeVC7dvQ1ylq/F9DRHbvJ4WMFVD1GtB7T8QmnWdhFy2sGJU2xxSsoPIEx85gnAXnw==" crossorigin="anonymous"></script>
    	<script src="$SWAGGER_URL/swagger-ui-bundle.min.js" integrity="sha512-PijkKcRp7VDW1K2S8nNgljcNRrEQmazUc8sPiVRMciEuNzJzz2KeKb2Cjz/HdjZrKwmEYEyhOFZlOi0xzqWdqg==" crossorigin="anonymous"></script>
    	<link rel="stylesheet" href="$SWAGGER_URL/swagger-ui.min.css" integrity="sha512-oSy4rNbpqDUaoXIoFxhwKj/LFbkzUzo/WWJrn9RcIFLr4wm30upZ8r1OmhSRVvndMFZ2bhHEc2HklxjlS/aOyQ==" crossorigin="anonymous" />
    	<script>
    		window.addEventListener("DOMContentLoaded", async () => {
    			const ui = SwaggerUIBundle({
                    urls: [
                      {url: "./specs/spec-crowdproj-ad-v1.yaml", name: "Ad API V1"},
                      // {url: "./specs-ad-v2.yaml", name: "Marketplace API V2"}
                    ],
                    "urls.primaryName": "Marketplace API V2",
                    dom_id: "#main",
                    deepLinking: true,
                    presets: [
                      SwaggerUIBundle.presets.apis,
                      SwaggerUIStandalonePreset
                    ],
                    plugins: [
                      SwaggerUIBundle.plugins.DownloadUrl
                    ],
                    layout: "StandaloneLayout"
    			});
    		});
    	</script>
    </head>
    <body>
    	<div id="main"></div>
    </body>
    </html>
""".trimIndent()

@ExperimentalEncodingApi
fun Routing.swagger(appConfig: CwpAdAppSettings) {
    get("/") {
        call.respondText(
            status = HttpStatusCode.OK,
            text = SWAGGER_HTML,
            contentType = ContentType.Text.Html.withParameter(
                name = "charset",
                value = Charsets.UTF_8.name
            ),
        )
    }
    get(Regex("^specs/.*\\.yaml\$")) {
        val res = RESOURCES[call.request.path().removePrefix("/")]
        if (res != null) {
            call.respondText(
                status = HttpStatusCode.OK,
                text = Base64.decode(res).decodeToString().replaceServers(appConfig.appUrls),
                contentType = ContentType("application", "yaml").withParameter(
                    name = "charset",
                    value = Charsets.UTF_8.name
                ),
            )
        } else {
            call.respondText(
                status = HttpStatusCode.NotFound,
                text = "Not Found",
            )
        }
    }

}

fun String.replaceServers(urls: List<String>): String = replace(
    Regex(
        "(?<=^servers:\$\\n)[\\s\\S]*?(?=\\n\\w+:\$)",
        setOf(RegexOption.MULTILINE, RegexOption.IGNORE_CASE)
    ),
    urls.joinToString(separator = "\n") { "  - url: $it" }
)

