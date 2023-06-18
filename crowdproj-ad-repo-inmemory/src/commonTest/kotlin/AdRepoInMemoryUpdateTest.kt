package com.crowdproj.ad.repo.inmemory

import com.crowdproj.ad.common.repo.IAdRepository
import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdUpdateTest


class AdRepoInMemoryUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = CwpAdRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
