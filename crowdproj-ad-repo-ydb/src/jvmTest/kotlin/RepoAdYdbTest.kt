package com.crowdproj.ad.repo.ydb

import com.crowdproj.ad.common.repo.IAdRepository
import com.crowdproj.ad.repo.tests.*
import ru.otus.otuskotlin.marketplace.backend.repo.tests.*

class RepoAdYdbCreateTest : RepoAdCreateTest() {
    override val repo: IAdRepository = YdbTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoAdYdbDeleteTest : RepoAdDeleteTest() {
    override val repo: IAdRepository = YdbTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdYdbReadTest : RepoAdReadTest() {
    override val repo: IAdRepository = YdbTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdYdbSearchTest : RepoAdSearchTest() {
    override val repo: IAdRepository = YdbTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdYdbUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = YdbTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
