package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.backend.repo.gremlin.mappers.*
import com.crowdproj.ad.backend.repo.gremlin.models.ArkadeCommandQuery
import com.crowdproj.ad.backend.repo.gremlin.models.ArkadeResultAd
import com.crowdproj.ad.backend.repo.gremlin.models.ArkadeResultBool
import com.crowdproj.ad.backend.repo.gremlin.models.ArkadeResultStr
import com.crowdproj.ad.common.helpers.asCwpAdError
import com.crowdproj.ad.common.helpers.errorRepoConcurrency
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.repo.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


class CwpAdRepoGremlin(
    private val conf: CwpAdRepoGremlinConf
) : IAdRepository {

    val initializedObjects: List<CwpAd>
    private val urlBuilder = URLBuilder().apply {
        host = conf.hosts
        port = conf.port
        path("/api/v1")
    }
    private val urlCommand = urlBuilder.clone().apply {
        appendPathSegments("command", conf.database)
    }.build()

    private val clientEngine = CIO.create {
        maxConnectionsCount = 1000
        endpoint {
            maxConnectionsPerRoute = 100
            pipelineMaxSize = 20
            keepAliveTime = 5000
            connectTimeout = 5000
            connectAttempts = 5
        }
//                https {
//                    // this: TLSConfigBuilder
//                    serverName = "api.ktor.io"
//                    cipherSuites = CIOCipherSuites.SupportedSuites
//                    trustManager = myCustomTrustManager
//                    random = mySecureRandom
//                    addKeyStore(myKeyStore, myKeyStorePassword)
//                }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val jsonMapper = Json {
        encodeDefaults = true
        explicitNulls = false
    }

    init {
        initializedObjects = runBlocking {
            ensureDatabase()
            conf.initObjects.map { save(it) }
        }
    }

    private suspend fun save(ad: CwpAd): CwpAd = performQueryAd(
        ArkadeCommandQuery(
            command = "g.addV('${CwpAdGremlinConst.LABEL_AD}')$PROPERTIES_SUBQUERY${gremlinPrepareResp()}",
            params = ad.toParams(),
        )
    ).let { it.data ?: throw Exception("Error requesting ${it.errors.map { it.message }}") }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val key = conf.randomUuid()
        val ad = rq.ad.copy(id = CwpAdId(key), lock = CwpAdLock(conf.randomUuid()))
        return performQueryAd(
            ArkadeCommandQuery(
                command = "g.addV('${CwpAdGremlinConst.LABEL_AD}')$PROPERTIES_SUBQUERY${gremlinPrepareResp()}",
                params = ad.toParams(),
            )
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != CwpAdId.NONE }?.asString() ?: return resultErrorEmptyId
        return performQueryAd(
            ArkadeCommandQuery(
                command = "g.V(keyAd)${gremlinPrepareResp()}",
                params = mapOf("keyAd" to key),
            )
        )
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        val key = rq.ad.id.takeIf { it != CwpAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.ad.lock.takeIf { it != CwpAdLock.NONE } ?: return resultErrorEmptyLock
        val newLock = CwpAdLock(conf.randomUuid())
        val newAd = rq.ad.copy(lock = newLock)
        return performQueryAd(
            ArkadeCommandQuery(
                command = "g.V(keyAd).as('a')" +
                        ".choose(" +
                        "select('a').values('${CwpAdGremlinConst.FIELD_LOCK}').is(oldLock)," +
                        "select('a')$PROPERTIES_SUBQUERY${gremlinPrepareResp()}," +
                        "select('a')${gremlinPrepareResp(CwpAdGremlinConst.RESULT_LOCK_FAILURE)}" +
                        ")",
                params = newAd.toParams() + mapOf("keyAd" to key, "oldLock" to oldLock.asString()),
            )
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != CwpAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != CwpAdLock.NONE } ?: return resultErrorEmptyLock
        return performQueryAd(
            ArkadeCommandQuery(
                command = "g.V(keyAd).as('a')" +
                        ".choose(" +
                        "select('a').values('${CwpAdGremlinConst.FIELD_LOCK}').is(oldLock)," +
                        "select('a').sideEffect(drop())${gremlinPrepareResp()}," +
                        "select('a')${gremlinPrepareResp(CwpAdGremlinConst.RESULT_LOCK_FAILURE)}" +
                        ")",
                params = mapOf("keyAd" to key, "oldLock" to oldLock.asString()),
            )
        )
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        val params: MutableMap<String, String> = mutableMapOf()
        val subQueries: MutableList<String> = mutableListOf()
        rq.ownerId.asString().takeIf { it.isNotBlank() }?.also {
            val label = CwpAdGremlinConst.FIELD_OWNER_ID
            params[label] = it
            subQueries.add(".has('$label', $label)")
        }
        rq.dealSide.takeIf { it != CwpAdDealSide.NONE }?.also {
            val label = CwpAdGremlinConst.FIELD_AD_TYPE
            params[label] = it.toDb()
            subQueries.add(".has('$label', $label)")
        }
        rq.titleFilter.takeIf { it.isNotBlank() }?.also {
            val label = CwpAdGremlinConst.FIELD_TITLE
            params[label] = it
            subQueries.add(".has('$label', containing($label))")
        }
        return performQueryAds(
            ArkadeCommandQuery(
                command = "g.V()${subQueries.joinToString(".")}${gremlinPrepareResp()}",
                params = params,
            )
        )
    }

//    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
//        val result = try {
//            g.V()
//                .apply { rq.ownerId.takeIf { it != CwpAdUserId.NONE }?.also { has(FIELD_OWNER_ID, it.asString()) } }
//                .apply { rq.dealSide.takeIf { it != CwpAdDealSide.NONE }?.also { has(FIELD_AD_TYPE, it.name) } }
//                .apply {
//                    rq.titleFilter.takeIf { it.isNotBlank() }?.also { has(FIELD_TITLE, TextP.containing(it)) }
//                }
//                .listCwpAd()
//                .toList()
//        } catch (e: Throwable) {
//            return DbAdsResponse(
//                isSuccess = false,
//                data = null,
//                errors = listOf(e.asCwpAdError())
//            )
//        }
//        return DbAdsResponse(
//            data = result.map { it.toCwpAd() },
//            isSuccess = true
//        )
//    }

    companion object : CwpAdRepoGremlinCompanion()

    private fun createClient() = HttpClient(clientEngine) {
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(
                        username = conf.user,
                        password = conf.pass,
                    )
                }
                sendWithoutRequest { request ->
                    true
                }
            }
        }
        install(ContentNegotiation) {
            json(jsonMapper)
        }
    }

    private suspend fun performQueryAd(query: ArkadeCommandQuery): DbAdResponse = runCatching {
        createClient().use { client ->
            val resp = client.post(url = urlCommand) {
                println("REQUEST: ${jsonMapper.encodeToString(ArkadeCommandQuery.serializer(), query)}")
                setBody(query)
                contentType(ContentType.Application.Json.withCharset(Charset.forName("UTF-8")))
            }
            println("RESPONSE AD: ${resp.status} ${resp.bodyAsText()}")
            val respBody = resp.body<ArkadeResultAd>()
            val obj = respBody.result?.firstOrNull()
            when {
                respBody.detail?.contains("Bucket with id") == true -> resultErrorNotFound(query.params["keyAd"] ?: "")
                resp.status != HttpStatusCode.OK -> DbAdResponse(
                    data = null,
                    isSuccess = false,
                    errors = listOf(
                        CwpAdError(
                            code = "db-error",
                            group = "backend-error",
                            message = "DB Error",
                            exception = Exception("Response error. HTTP code: ${resp.status.value}, message: ${resp.bodyAsText()}")
                        )
                    ),
                )
                obj == null -> resultErrorNotFound(query.params["keyAd"] ?: "")
                obj.mark == CwpAdGremlinConst.RESULT_LOCK_FAILURE -> DbAdResponse(
                    data = obj.toCommon(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(
                        expectedLock = query.params["oldLock"]?.let { CwpAdLock(it) } ?: CwpAdLock.NONE,
                        actualLock = obj.lock?.let { CwpAdLock(it) },
                    )
                    )
                )

                else -> DbAdResponse(
                    data = obj.toCommon(),
                    isSuccess = true,
                )
            }
        }
    }.getOrElse { e ->
        DbAdResponse(
            isSuccess = false,
            data = null,
            errors = listOf(e.asCwpAdError(code = "db-exception"))
        )
    }

    private suspend fun performQueryAds(query: ArkadeCommandQuery): DbAdsResponse = runCatching {
        createClient().use { client ->
            val resp = client.post(url = urlCommand) {
                println("REQUEST: ${jsonMapper.encodeToString(ArkadeCommandQuery.serializer(), query)}")
                setBody(query)
                contentType(ContentType.Application.Json.withCharset(Charset.forName("UTF-8")))
            }
            if (resp.status == HttpStatusCode.OK) {
                println("RESPONSE: ${resp.bodyAsText()}")
                DbAdsResponse(
                    data = resp.body<ArkadeResultAd>().result?.map { it.toCommon() } ?: emptyList(),
                    isSuccess = true,
                )
            } else {
                DbAdsResponse(
                    data = null,
                    isSuccess = false,
                    errors = listOf(
                        CwpAdError(
                            code = "db-error",
                            group = "backend-error",
                            message = "DB Error",
                            exception = Exception("Response error. HTTP code: ${resp.status.value}, message: ${resp.bodyAsText()}")
                        )
                    ),
                )
            }
        }
    }.getOrElse { e ->
        DbAdsResponse(
            isSuccess = false,
            data = null,
            errors = listOf(e.asCwpAdError(code = "db-exception"))
        )
    }

    private suspend fun ensureDatabase() {
        val dbName = conf.database
        if (dbName.isBlank()) throw Exception("Database name is not set")
        if (dbName.matches(Regex("[^\\w_]+"))) throw Exception("Database name contains wrong symbols")
        createClient().use { client ->
            val urlExists = urlBuilder.clone().run { appendPathSegments("exists", dbName); build() }
            val respExists = client.get(url = urlExists)
            if (respExists.status != HttpStatusCode.OK) {
                throw Exception("Error connecting database")
            }
            if (respExists.body<ArkadeResultBool>().result == true) return

            val urlCreateDb = urlBuilder.clone().run { appendPathSegments("server"); build() }
            val respCreateDb = client.post(url = urlCreateDb) {
                setBody("{\"command\":\"create database ${dbName}\"}")
                contentType(ContentType.Application.Json.withCharset(Charset.forName("UTF-8")))
            }
            if (respCreateDb.status != HttpStatusCode.OK) throw Exception("Error creating database '$dbName'")
            val dataCreateDb = respCreateDb.body<ArkadeResultStr>().result
            if (dataCreateDb != "ok") throw Exception("Error creating database '$dbName' with result '$dataCreateDb'")
        }
    }
}
