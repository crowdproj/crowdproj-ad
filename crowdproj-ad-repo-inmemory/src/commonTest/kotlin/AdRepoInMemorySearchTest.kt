package com.crowdproj.ad.repo.inmemory

import com.crowdproj.ad.common.repo.IAdRepository
import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdSearchTest



class AdRepoInMemorySearchTest : RepoAdSearchTest() {
    override val repo: IAdRepository = CwpAdRepoInMemory(
        initObjects = initObjects
    )
}
