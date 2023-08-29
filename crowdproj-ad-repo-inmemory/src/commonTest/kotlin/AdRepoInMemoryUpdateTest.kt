package com.crowdproj.ad.repo.inmemory

import com.crowdproj.ad.common.repo.IAdRepository
import com.crowdproj.ad.repo.tests.RepoAdUpdateTest



class AdRepoInMemoryUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = CwpAdRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
