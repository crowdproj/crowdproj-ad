package com.crowdproj.ad.api.v1.mappers

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.api.v1.models.*

fun CwpAdContext.toApi(): IResponseAd? = when (command) {
    CwpAdCommand.NONE -> null
    CwpAdCommand.CREATE -> toApiCreate()
    CwpAdCommand.READ -> toApiRead()
    CwpAdCommand.UPDATE -> toApiUpdate()
    CwpAdCommand.DELETE -> toApiDelete()
    CwpAdCommand.SEARCH -> toApiSearch()
    CwpAdCommand.OFFERS -> toApiOffers()
}

fun CwpAdContext.toApiCreate() = AdCreateResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    ad = toApiAd(this.adResponse),
)

fun CwpAdContext.toApiRead() = AdReadResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    ad = toApiAd(this.adResponse),
)

fun CwpAdContext.toApiUpdate() = AdUpdateResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    ad = toApiAd(this.adResponse),
)

fun CwpAdContext.toApiDelete() = AdDeleteResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    ad = toApiAd(this.adResponse),
)

fun CwpAdContext.toApiSearch() = AdSearchResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    ads = adsResponse.mapNotNull { toApiAd(it) }.takeIf { it.isNotEmpty() },
)

fun CwpAdContext.toApiOffers() = AdOffersResponse(
    requestId = toApiRequestId(),
    result = toApiResult(),
    errors = toApiErrors(),
    ad = toApiAd(this.adResponse),
    ads = adsResponse.mapNotNull { toApiAd(it) }.takeIf { it.isNotEmpty() },
)

private fun toApiAd(ad: CwpAd): AdResponseObject? = if (ad.isEmpty()) {
    null
} else {
    AdResponseObject(
        id = ad.id.takeIf { it != CwpAdId.NONE }?.asString(),
        title = ad.title.trString(),
        description = ad.description.trString(),
        adType = ad.adType.toApiAdType(),
        visibility = ad.visibility.toApiAdVisibility(),
        productId = ad.productId.takeIf { it != CwpAdProductId.NONE }?.asString(),
        ownerId = ad.ownerId.takeIf { it != CwpAdUserId.NONE }?.asString(),
        lock = ad.lock.takeIf { it != CwpAdLock.NONE }?.asString(),
        permissions = ad.permissionsClient.toApiPermissions(),
    )
}

private fun Set<CwpAdPermissionClient>.toApiPermissions(): Set<AdPermissions>? = this
    .map { it.toApiPermission() }
    .takeIf { it.isNotEmpty() }
    ?.toSet()

private fun CwpAdPermissionClient.toApiPermission(): AdPermissions = when(this) {
    CwpAdPermissionClient.READ -> AdPermissions.READ
    CwpAdPermissionClient.UPDATE -> AdPermissions.UPDATE
    CwpAdPermissionClient.DELETE -> AdPermissions.DELETE
    CwpAdPermissionClient.MAKE_VISIBLE_PUBLIC -> AdPermissions.MAKE_VISIBLE_PUBLIC
    CwpAdPermissionClient.MAKE_VISIBLE_GROUP -> AdPermissions.MAKE_VISIBLE_GROUP
    CwpAdPermissionClient.MAKE_VISIBLE_OWNER -> AdPermissions.MAKE_VISIBLE_OWN
}

private fun CwpAdVisibility.toApiAdVisibility(): AdVisibility? = when(this) {
    CwpAdVisibility.NONE -> null
    CwpAdVisibility.VISIBLE_TO_OWNER -> AdVisibility.OWNER_ONLY
    CwpAdVisibility.VISIBLE_TO_GROUP -> AdVisibility.REGISTERED_ONLY
    CwpAdVisibility.VISIBLE_PUBLIC -> AdVisibility.PUBLIC
}

private fun CwpAdDealSide.toApiAdType(): DealSide? = when(this) {
    CwpAdDealSide.NONE -> null
    CwpAdDealSide.DEMAND -> DealSide.DEMAND
    CwpAdDealSide.SUPPLY -> DealSide.SUPPLY
}

private fun CwpAdContext.toApiErrors(): List<Error>? = errors.map { it.toApiError() }.takeIf { it.isNotEmpty() }
private fun CwpAdError.toApiError() = Error(
    code = this.code.trString(),
    group = this.group.trString(),
    field = this.field.trString(),
    title = this.message.trString(),
    description = null,
)

private fun String.trString(): String? = takeIf { it.isNotBlank() }

private fun CwpAdContext.toApiResult(): ResponseResult? = when (this.state) {
    CwpAdState.NONE -> null
    CwpAdState.RUNNING -> ResponseResult.SUCCESS
    CwpAdState.FAILING -> ResponseResult.ERROR
    CwpAdState.FINISHING -> ResponseResult.SUCCESS
}

private fun CwpAdContext.toApiRequestId(): String? = this.requestId.takeIf { it != CwpAdRequestId.NONE }?.asString()
