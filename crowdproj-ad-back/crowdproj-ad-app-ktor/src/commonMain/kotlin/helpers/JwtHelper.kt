package com.crowdproj.ad.app.helpers

import com.crowdproj.ad.common.models.CwpAdUserId
import com.crowdproj.ad.common.permissions.CwpAdPrincipalModel
import com.crowdproj.ad.common.permissions.CwpAdUserGroups
import io.ktor.server.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun ApplicationRequest.jwt2principal(): CwpAdPrincipalModel = this.header("x-jwt-payload")
    ?.let { jwtHeader ->
        val jwtJson = Base64.decode(jwtHeader).decodeToString()
        println("JWT JSON PAYLOAD: $jwtJson")
        val jwtObj = jsMapper.decodeFromString(JwtPayload.serializer(), jwtJson)
        jwtObj.toPrincipal()
    }
    ?: run {
        println("No jwt found in headers")
        CwpAdPrincipalModel.NONE
    }

private val jsMapper = Json {
    ignoreUnknownKeys = true
}

@Serializable
private data class JwtPayload(
    val aud: List<String>? = null,
    val sub: String? = null,
    @SerialName("family_name")
    val familyName: String? = null,
    @SerialName("given_name")
    val givenName: String? = null,
    @SerialName("middle_name")
    val middleName: String? = null,
    val groups: List<String>? = null,
)

private fun JwtPayload.toPrincipal(): CwpAdPrincipalModel = CwpAdPrincipalModel(
    id = sub?.let { CwpAdUserId(it) } ?: CwpAdUserId.NONE,
    fname = givenName ?: "",
    mname = middleName ?: "",
    lname = familyName ?: "",
    groups = groups?.mapNotNull { it.toPrincipalGroup() }?.toSet() ?: emptySet(),
)

private fun String?.toPrincipalGroup(): CwpAdUserGroups? = when (this?.uppercase()) {
    "USER" -> CwpAdUserGroups.USER
    "ADMIN_AD" -> CwpAdUserGroups.ADMIN_AD
    "MODERATOR_MP" -> CwpAdUserGroups.MODERATOR_MP
    "TEST" -> CwpAdUserGroups.TEST
    "BAN_AD" -> CwpAdUserGroups.BAN_AD
    // TODO сделать обработку ошибок
    else -> null
}
