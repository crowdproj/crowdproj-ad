package com.crowdproj.ad.repo.inmemory

import com.crowdproj.ad.common.repo.IAdRepository
import com.crowdproj.ad.repo.tests.RepoAdReadTest

class AdRepoInMemoryReadTest: RepoAdReadTest() {
    override val repo: IAdRepository = CwpAdRepoInMemory(
        initObjects = initObjects
    )
}
