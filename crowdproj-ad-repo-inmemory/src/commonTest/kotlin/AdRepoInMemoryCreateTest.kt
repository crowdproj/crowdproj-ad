package com.crowdproj.ad.repo.inmemory

import com.crowdproj.ad.repo.tests.RepoAdCreateTest


class AdRepoInMemoryCreateTest : RepoAdCreateTest() {
    override val repo = CwpAdRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
