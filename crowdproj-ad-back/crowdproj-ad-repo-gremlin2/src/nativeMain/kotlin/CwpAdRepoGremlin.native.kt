package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.backend.repo.gremlin.mappers.adToStore
import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.repo.*
import gremlinRust.*
import kotlinx.cinterop.*

@OptIn(ExperimentalStdlibApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CwpAdRepoGremlin actual constructor(
    private val conf: CwpAdRepoGremlinConf
) : IAdRepository, AutoCloseable {

    @OptIn(ExperimentalForeignApi::class)
    private val connection: CValue<CwpAdGremConnection> by lazy {
        println("KT CONNECTION")
        memScoped {
            val connConf = cValue<RepoConnConf> {
                host = conf.hosts.cstr.ptr
                port = conf.port.toUShort()
                user = conf.user.cstr.ptr
                pass = conf.pass.cstr.ptr
            }
            println("KT CALL RS CONN")
            connConf.usePinned {
                cwp_ad_repo_conn(connConf).apply {
                    println("KT CALL RS CONN DONE")
                }
            }
        }
    }

    actual val initializedObjects: List<CwpAd>
        get() = TODO("Not yet implemented ${conf.hosts}")

    @OptIn(ExperimentalForeignApi::class)
    actual fun save(ad: CwpAd): CwpAd = memScoped {
        println("KT CALL SAVE")
        usePinned {
            cwp_ad_grem_save(connection, adToStore(ad))
        }
        println("KT CALL SAVE DONE")
        return ad
    }

    actual override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        TODO("Not yet implemented")
    }

    actual override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        TODO("Not yet implemented")
    }

    actual override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        TODO("Not yet implemented")
    }

    actual override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        TODO("Not yet implemented")
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    actual override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun close() {
        println("KT DISCONNECT")
        cwp_ad_repo_disconnect(connection)
        println("KT DISCONNECT DONE")
    }

}
