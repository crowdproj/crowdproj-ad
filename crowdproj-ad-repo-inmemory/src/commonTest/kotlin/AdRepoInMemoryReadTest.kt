package com.crowdproj.ad.repo.inmemory

import com.crowdproj.ad.common.repo.IAdRepository
import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdReadTest

class AdRepoInMemoryReadTest: RepoAdReadTest() {
    override val repo: IAdRepository = CwpAdRepoInMemory(
        initObjects = initObjects
    )
}
