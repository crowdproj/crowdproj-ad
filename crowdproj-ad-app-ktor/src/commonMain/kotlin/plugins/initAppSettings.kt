package com.crowdproj.ad.app.plugins

import com.crowdproj.ad.common.config.CwpAdCorSettings
import com.crowdproj.ad.repo.inmemory.CwpAdRepoInMemory
import configs.CwpAdAppSettings
import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.CwpAdRepoStub

fun Application.initAppSettings(): CwpAdAppSettings {
    return CwpAdAppSettings(
        appUrls = environment.config
            .propertyOrNull("ktor.urls")
            ?.getList()
            ?.filter { it.isNotBlank() }
            ?: emptyList(),
        corSettings = CwpAdCorSettings(
            repoProd = CwpAdRepoInMemory(),
            repoTest = CwpAdRepoInMemory(),
            repoStub = CwpAdRepoStub(),
        )
    )
}
