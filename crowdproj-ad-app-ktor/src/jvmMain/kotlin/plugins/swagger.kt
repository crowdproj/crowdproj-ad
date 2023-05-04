package com.crowdproj.ad.app.plugins

import configs.CwpAdAppSettings
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual fun Routing.swagger(appConfig: CwpAdAppSettings) {
    get("/spec-crowdproj-ad-v1.yaml") {
        val origTxt: String = withContext(Dispatchers.IO) {
            this::class.java.classLoader
                .getResource("specs/spec-crowdproj-ad-v1.yaml")
                ?.readText()
        } ?: ""
        val response = origTxt.replace(
            Regex(
                "(?<=^servers:\$\\n).*(?=\\ntags:\$)",
                setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE, RegexOption.IGNORE_CASE)
            ),
            appConfig.appUrls.joinToString(separator = "\n") { "  - url: $it" }
        )
        call.respondText { response }
    }

    route("/") {
        preCompressed(CompressedFileType.GZIP) {
            staticResources("/swagger-initializer.js", "", "swagger-initializer.js")
            staticResources("/", "specs")
            staticResources("/", "swagger-ui", index = "index.html")
//            defaultResource("index.html", "swagger-ui")
//            static {
//                staticBasePackage = "specs"
//                resources(".")
//            }
//            static {
//                preCompressed(CompressedFileType.GZIP) {
//                    staticBasePackage = "swagger-ui"
//                    resources(".")
//                }
//            }
        }
    }
}
