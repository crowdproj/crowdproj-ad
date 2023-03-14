package configs

import com.crowdproj.ad.common.config.CwpAdCorSettings
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import kotlinx.serialization.json.Json

data class CwpAdAppSettings(
    var clientEngine: HttpClientEngine = CIO.create(),
    val json: Json = Json,
    val corSettings: CwpAdCorSettings = CwpAdCorSettings(),
    val appUrls: List<String> = listOf(),
)
