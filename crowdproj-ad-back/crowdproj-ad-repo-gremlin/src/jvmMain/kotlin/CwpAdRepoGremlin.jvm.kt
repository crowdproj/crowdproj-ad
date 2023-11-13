package com.crowdproj.ad.backend.repository.gremlin

import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_AD_TYPE
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_LOCK
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_OWNER_ID
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_TITLE
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_TMP_RESULT
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.RESULT_LOCK_FAILURE
import com.crowdproj.ad.backend.repository.gremlin.mappers.addCwpAd
import com.crowdproj.ad.backend.repository.gremlin.mappers.label
import com.crowdproj.ad.backend.repository.gremlin.mappers.listCwpAd
import com.crowdproj.ad.backend.repository.gremlin.mappers.toCwpAd
import com.crowdproj.ad.common.helpers.asCwpAdError
import com.crowdproj.ad.common.helpers.errorRepoConcurrency
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.repo.*
import org.apache.tinkerpop.gremlin.driver.Cluster
import org.apache.tinkerpop.gremlin.driver.exception.ResponseException
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal
import org.apache.tinkerpop.gremlin.process.traversal.TextP
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.`__` as gr

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CwpAdRepoGremlin actual constructor(private val conf: CwpAdRepoGremlinConf) : IAdRepository {

    actual val initializedObjects: List<CwpAd>

    private val cluster by lazy {
        Cluster.build().apply {
            addContactPoints(*conf.hosts.split(Regex("\\s*,\\s*")).toTypedArray())
            port(conf.port)
            credentials(conf.user, conf.pass)
            enableSsl(conf.enableSsl)
        }.create()
    }
    private val g by lazy { traversal().withRemote(DriverRemoteConnection.using(cluster)) }

    init {
        if (conf.mustClean) {
            g.V().drop().iterate()
        }
        initializedObjects = conf.initObjects.map { save(it) }
    }

    actual fun save(ad: CwpAd): CwpAd = g.addV(ad.label())
        .addCwpAd(ad)
        .listCwpAd()
        .next()
        ?.toCwpAd()
        ?: throw RuntimeException("Cannot initialize object $ad")

    actual override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val key = conf.randomUuid()
        val ad = rq.ad.copy(id = CwpAdId(key), lock = CwpAdLock(conf.randomUuid()))
        val dbRes = try {
            g.addV(ad.label())
                .addCwpAd(ad)
                .listCwpAd()
                .toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbAdResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asCwpAdError())
            )
        }
        return when (dbRes.size) {
            0 -> resultErrorNotFound(key)
            1 -> DbAdResponse(
                data = dbRes.first().toCwpAd(),
                isSuccess = true,
            )

            else -> errorDuplication(key)
        }
    }

    actual override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != CwpAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val dbRes = try {
            g.V(key).listCwpAd().toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbAdResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asCwpAdError())
            )
        }
        return when (dbRes.size) {
            0 -> resultErrorNotFound(key)
            1 -> DbAdResponse(
                data = dbRes.first().toCwpAd(),
                isSuccess = true,
            )

            else -> errorDuplication(key)
        }
    }

    actual override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        val key = rq.ad.id.takeIf { it != CwpAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.ad.lock.takeIf { it != CwpAdLock.NONE } ?: return resultErrorEmptyLock
        val newLock = CwpAdLock(conf.randomUuid())
        val newAd = rq.ad.copy(lock = newLock)
        val dbRes = try {
            g
                .V(key)
                .`as`("a")
                .choose(
                    gr.select<Vertex, Any>("a")
                        .values<String>(FIELD_LOCK)
                        .`is`(oldLock.asString()),
                    gr.select<Vertex, Vertex>("a").addCwpAd(newAd).listCwpAd(),
                    gr.select<Vertex, Vertex>("a").listCwpAd(result = RESULT_LOCK_FAILURE)
                )
                .toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbAdResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asCwpAdError())
            )
        }
        val adResult = dbRes.firstOrNull()?.toCwpAd()
        return when {
            adResult == null -> resultErrorNotFound(key)
            dbRes.size > 1 -> errorDuplication(key)
            adResult.lock != newLock -> DbAdResponse(
                data = adResult,
                isSuccess = false,
                errors = listOf(
                    errorRepoConcurrency(
                        expectedLock = oldLock,
                        actualLock = adResult.lock,
                    ),
                )
            )

            else -> DbAdResponse(
                data = adResult,
                isSuccess = true,
            )
        }
    }

    actual override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != CwpAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != CwpAdLock.NONE } ?: return resultErrorEmptyLock
        val dbRes = try {
            g
                .V(key)
                .`as`("a")
                .choose(
                    gr.select<Vertex, Vertex>("a")
                        .values<String>(FIELD_LOCK)
                        .`is`(oldLock.asString()),
                    gr.select<Vertex, Vertex>("a")
                        .sideEffect(gr.drop<Vertex>())
                        .listCwpAd(),
                    gr.select<Vertex, Vertex>("a")
                        .listCwpAd(result = RESULT_LOCK_FAILURE)
                )
                .toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbAdResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asCwpAdError())
            )
        }
        val dbFirst = dbRes.firstOrNull()
        val adResult = dbFirst?.toCwpAd()
        return when {
            adResult == null -> resultErrorNotFound(key)
            dbRes.size > 1 -> errorDuplication(key)
            dbFirst[FIELD_TMP_RESULT] == RESULT_LOCK_FAILURE -> DbAdResponse(
                data = adResult,
                isSuccess = false,
                errors = listOf(
                    errorRepoConcurrency(
                        expectedLock = oldLock,
                        actualLock = adResult.lock,
                    ),
                )
            )

            else -> DbAdResponse(
                data = adResult,
                isSuccess = true,
            )
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    actual override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        val result = try {
            g.V()
                .apply { rq.ownerId.takeIf { it != CwpAdUserId.NONE }?.also { has(FIELD_OWNER_ID, it.asString()) } }
                .apply { rq.dealSide.takeIf { it != CwpAdDealSide.NONE }?.also { has(FIELD_AD_TYPE, it.name) } }
                .apply {
                    rq.titleFilter.takeIf { it.isNotBlank() }?.also { has(FIELD_TITLE, TextP.containing(it)) }
                }
                .listCwpAd()
                .toList()
        } catch (e: Throwable) {
            return DbAdsResponse(
                isSuccess = false,
                data = null,
                errors = listOf(e.asCwpAdError())
            )
        }
        return DbAdsResponse(
            data = result.map { it.toCwpAd() },
            isSuccess = true
        )
    }

    companion object: CwpAdRepoGremlinCompanion()
}
