package com.crowdproj.ad.repo.inmemory

import com.crowdproj.ad.common.repo.IAdRepository
import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdDeleteTest


class AdRepoInMemoryDeleteTest : RepoAdDeleteTest() {
    override val repo: IAdRepository = CwpAdRepoInMemory(
        initObjects = initObjects
    )
}
