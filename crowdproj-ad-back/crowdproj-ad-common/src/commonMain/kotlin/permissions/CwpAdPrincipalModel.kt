package com.crowdproj.ad.common.permissions

import com.crowdproj.ad.common.models.CwpAdUserId

data class CwpAdPrincipalModel(
    val id: CwpAdUserId = CwpAdUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<CwpAdUserGroups> = emptySet()
) {
    companion object {
        val NONE = CwpAdPrincipalModel()
    }
}
