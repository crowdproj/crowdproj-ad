package com.crowdproj.ad.stubs

import com.crowdproj.ad.common.models.*


object CwpAdStub {
    fun get() = CwpAd(
        id = CwpAdId("666"),
        title = "Требуется болт",
        description = "Требуется болт 100x5 с шестигранной шляпкой",
        ownerId = CwpAdUserId("user-1"),
        adType = CwpAdDealSide.DEMAND,
        visibility = CwpAdVisibility.VISIBLE_PUBLIC,
        permissionsClient = mutableSetOf(
            CwpAdPermissionClient.READ,
            CwpAdPermissionClient.UPDATE,
            CwpAdPermissionClient.DELETE,
            CwpAdPermissionClient.MAKE_VISIBLE_PUBLIC,
            CwpAdPermissionClient.MAKE_VISIBLE_GROUP,
            CwpAdPermissionClient.MAKE_VISIBLE_OWNER,
        )
    )

    fun prepareResult(block: CwpAd.() -> Unit): CwpAd = get().apply(block)

    fun prepareSearchList(filter: String, type: CwpAdDealSide) = listOf(
        cwpAdDemand("d-666-01", filter, type),
        cwpAdDemand("d-666-02", filter, type),
        cwpAdDemand("d-666-03", filter, type),
        cwpAdDemand("d-666-04", filter, type),
        cwpAdDemand("d-666-05", filter, type),
        cwpAdDemand("d-666-06", filter, type),
    )

    fun prepareOffersList(filter: String, type: CwpAdDealSide) = listOf(
        cwpAdSupply("s-666-01", filter, type),
        cwpAdSupply("s-666-02", filter, type),
        cwpAdSupply("s-666-03", filter, type),
        cwpAdSupply("s-666-04", filter, type),
        cwpAdSupply("s-666-05", filter, type),
        cwpAdSupply("s-666-06", filter, type),
    )

    private fun cwpAdDemand(id: String, filter: String, type: CwpAdDealSide) =
        cwpAd(get(), id = id, filter = filter, type = type)

    private fun cwpAdSupply(id: String, filter: String, type: CwpAdDealSide) =
        cwpAd(get(), id = id, filter = filter, type = type)

    private fun cwpAd(base: CwpAd, id: String, filter: String, type: CwpAdDealSide) = base.copy(
        id = CwpAdId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        adType = type,
    )

}
