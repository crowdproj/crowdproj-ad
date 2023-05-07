package com.crowdproj.ad.repo.inmemory

import com.crowdproj.ad.common.repo.IAdRepository
import com.crowdproj.ad.repo.tests.RepoAdOffersTest

class AdRepoInMemoryOffersTest : RepoAdOffersTest() {
    override val repo: IAdRepository = CwpAdRepoInMemory(
        initObjects = initObjects
    )
}
