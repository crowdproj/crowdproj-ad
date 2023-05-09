package com.crowdproj.ad.common.config

import com.crowdproj.ad.common.repo.IAdRepository

data class CwpAdCorSettings(
    val repoTest: IAdRepository = IAdRepository.NONE,
    val repoStub: IAdRepository = IAdRepository.NONE,
    val repoProd: IAdRepository = IAdRepository.NONE,
) {
    companion object {
        val NONE = CwpAdCorSettings()
    }
}
