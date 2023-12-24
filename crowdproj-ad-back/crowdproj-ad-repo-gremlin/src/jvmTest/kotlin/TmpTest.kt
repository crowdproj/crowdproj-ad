package com.crowdproj.ad.backend.repo.gremlin

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.charsets.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TmpTest {

//    @Test
//    fun z() {
//        val authProp = AuthProperties().apply {
//            with(AuthProperties.Property.USERNAME, "root")
//            with(AuthProperties.Property.PASSWORD, "root_root")
//        }
//        val cluster = Cluster.build()
//            .addContactPoints("localhost")
//            .port(8182)
////            .credentials("root", "playwithdata")
//            .authProperties(authProp)
//            .create()
//        // Должно работать таким образом, но на текущий момент не работает из-за бага в ArcadeDb
//        // Тест в ArcadeDb задизейблен:
//        // https://github.com/ArcadeData/arcadedb/blob/main/gremlin/src/test/java/com/arcadedb/server/gremlin/ConnectRemoteGremlinServer.java
//        traversal()
//            .withRemote(DriverRemoteConnection.using(cluster, "g"))
//            .use { g ->
//                val userId = g
//                    .addV("User")
//                    .property(VertexProperty.Cardinality.single, "name", "Evan")
//                    .next()
//                    .id()
//                println("UserID: $userId")
//            }
//    }

    @Test
    fun httpArcadeDb() = runTest {
        val clientEngine = CIO.create {
            // this: CIOEngineConfig
            maxConnectionsCount = 1000
            endpoint {
                // this: EndpointConfig
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
        HttpClient(clientEngine) {
            install(Auth) {
                basic {
                    credentials {
                        BasicAuthCredentials(
                            username = "root",
                            password = "root_root"
                        )
                    }
                }
            }
        }.use { client ->
            // status
//            val r1 = client.get(url = Url("http://localhost:2480/api/v1/ready"))
//            assertEquals(HttpStatusCode.NoContent, r1.status)

            // info
//            val r2 = client.get(url = Url("http://localhost:2480/api/v1/server"))
//            val r2body = r2.bodyAsText()
//            assertEquals(HttpStatusCode.OK, r2.status)
//            println(r2body)

            // create database
//            val r3 = client.post(url = Url("http://localhost:2480/api/v1/server")) {
//                setBody("""{"command": "create database yyy"}""")
//                contentType(ContentType.Application.Json.withCharset(Charset.defaultCharset()))
//            }
//            val r3body = r3.bodyAsText()
//            assertEquals(HttpStatusCode.OK, r3.status)
//            println(r3body)

            val r4 = client.post(url = Url("http://localhost:2480/api/v1/command/graph")) {
                setBody("""{"language":"gremlin","command":"g.addV('AD').property(single, 'title', title).property(single, 'description', description).property(single, 'lock', lock).property(single, 'ownerId', ownerId).property(single, 'adType', adType).property(single, 'visibility', visibility).property(single, 'productId', productId)","params":{"title":"title 1","description":"descr 1","lock":"","ownerId":"2342423523","adType":"DEMAND","visibility":"PUBLIC","productId":"234234324"}}""".trimIndent())
//                setBody("""{
//                    "language":"gremlin",
//                    "command":"g.addV(label).property(single, 'title', title).property(single, 'description', description).property(single, 'lock', lock).property(single, 'ownerId', ownerId).property(single, 'adType', adType).property(single, 'visibility', visibility).property(single, 'productId', productId)",
//                    "params":{
//                        "label":"AD",
//                        "title":"title 1",
//                        "description":"descr 1",
//                        "lock":"",
//                        "ownerId":"2342423523",
//                        "adType":"DEMAND",
//                        "visibility":"PUBLIC",
//                        "productId":"dfsdf"
//                    }
//                }""".trimIndent())
                contentType(ContentType.Application.Json.withCharset(Charset.defaultCharset()))
            }
            val r4body = r4.bodyAsText()
            assertEquals(HttpStatusCode.OK, r4.status)
            println(r4body)
        }
    }
}
