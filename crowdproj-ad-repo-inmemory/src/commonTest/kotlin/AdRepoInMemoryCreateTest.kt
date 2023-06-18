package com.crowdproj.ad.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdCreateTest

class AdRepoInMemoryCreateTest : RepoAdCreateTest() {
    override val repo = CwpAdRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
