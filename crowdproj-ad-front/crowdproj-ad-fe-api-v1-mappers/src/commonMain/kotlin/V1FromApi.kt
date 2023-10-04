package com.crowdproj.ad.api.v1.mappers

import com.crowdproj.ad.api.v1.models.*
import com.crowdproj.ad.common.CwpAdFeContext
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.stubs.CwpAdStubs

fun CwpAdFeContext.fromApi(request: IRequestAd) = when (request) {
    is AdCreateRequest -> fromApi(request)
    is AdReadRequest -> fromApi(request)
    is AdUpdateRequest -> fromApi(request)
    is AdDeleteRequest -> fromApi(request)
    is AdSearchRequest -> fromApi(request)
    is AdOffersRequest -> fromApi(request)
}

fun CwpAdFeContext.fromApi(request: AdCreateRequest) {
    resolveOperation(request)
    fromApiAdCreate(request.ad)
    fromApiDebug(request)
}

fun CwpAdFeContext.fromApi(request: AdReadRequest) {
    resolveOperation(request)
    fromApiAdRead(request.ad)
    fromApiDebug(request)
}

fun CwpAdFeContext.fromApi(request: AdUpdateRequest) {
    resolveOperation(request)
    fromApiAdUpdate(request.ad)
    fromApiDebug(request)
}

fun CwpAdFeContext.fromApi(request: AdDeleteRequest) {
    resolveOperation(request)
    fromApiAdDelete(request.ad)
    fromApiDebug(request)
}

fun CwpAdFeContext.fromApi(request: AdSearchRequest) {
    resolveOperation(request)
    fromApiAdSearch(request.adFilter)
    fromApiDebug(request)
}

fun CwpAdFeContext.fromApi(request: AdOffersRequest) {
    resolveOperation(request)
    fromApiAdOffers(request.ad)
    fromApiDebug(request)
}

private fun CwpAdFeContext.fromApiAdCreate(ad: AdCreateObject?) {
//    this.adRequest = ad?.let {
//        CwpAd(
//            adType = it.adType.fromApi(),
//            title = it.title.fromApiTitle(),
//            description = it.description.fromApiDescription(),
//            visibility = it.visibility.fromApiVisibility(),
//            productId = it.productId.fromApiProductId(),
//        )
//    } ?: CwpAd()
}

private fun CwpAdFeContext.fromApiAdRead(ad: AdReadObject?) {
//    this.adRequest.id = ad?.id?.toAdId() ?: CwpAdId.NONE
}

private fun CwpAdFeContext.fromApiAdUpdate(ad: AdUpdateObject?) {
//    this.adRequest = ad?.let {
//        CwpAd(
//            id = it.id.toAdId(),
//            lock = it.lock.toAdLock(),
//            adType = it.adType.fromApi(),
//            title = it.title.fromApiTitle(),
//            description = it.description.fromApiDescription(),
//            visibility = it.visibility.fromApiVisibility(),
//            productId = it.productId.fromApiProductId(),
//        )
//    } ?: CwpAd()
}

private fun CwpAdFeContext.fromApiAdDelete(ad: AdDeleteObject?) {
//    this.adRequest.id = ad?.id?.toAdId() ?: CwpAdId.NONE
//    this.adRequest.lock = ad?.lock?.toAdLock() ?: CwpAdLock.NONE
}

private fun CwpAdFeContext.fromApiAdSearch(filter: AdSearchFilter?) {
//    this.adFilterRequest = CwpAdFilter(
//        searchString = filter?.searchString ?: "",
//    )
}

private fun CwpAdFeContext.fromApiAdOffers(ad: AdReadObject?) {
//    this.adRequest.id = ad?.id?.toAdId() ?: CwpAdId.NONE
}

private fun String?.toAdId() = this?.let { CwpAdId(it) } ?: CwpAdId.NONE
private fun String?.toAdLock() = this?.let { CwpAdLock(it) } ?: CwpAdLock.NONE
private fun String?.fromApiTitle() = this ?: ""
private fun String?.fromApiDescription() = this ?: ""
private fun String?.fromApiProductId() = this?.let { CwpAdProductId(it) } ?: CwpAdProductId.NONE

private fun DealSide?.fromApi(): CwpAdDealSide = when (this) {
    null -> CwpAdDealSide.NONE
    DealSide.DEMAND -> CwpAdDealSide.DEMAND
    DealSide.SUPPLY -> CwpAdDealSide.SUPPLY
}

private fun AdRequestDebugMode?.fromApiWorkMode(): CwpAdWorkMode = when (this) {
    AdRequestDebugMode.PROD -> CwpAdWorkMode.PROD
    AdRequestDebugMode.TEST -> CwpAdWorkMode.TEST
    AdRequestDebugMode.STUB -> CwpAdWorkMode.STUB
    null -> CwpAdWorkMode.NONE
}

private fun AdRequestDebugStubs?.fromApiStubCase(): CwpAdStubs = when (this) {
    AdRequestDebugStubs.SUCCESS -> CwpAdStubs.SUCCESS
    AdRequestDebugStubs.NOT_FOUND -> CwpAdStubs.NOT_FOUND
    AdRequestDebugStubs.BAD_ID -> CwpAdStubs.BAD_ID
    AdRequestDebugStubs.BAD_TITLE -> CwpAdStubs.BAD_TITLE
    AdRequestDebugStubs.BAD_DESCRIPTION -> CwpAdStubs.BAD_DESCRIPTION
    AdRequestDebugStubs.BAD_VISIBILITY -> CwpAdStubs.BAD_VISIBILITY
    AdRequestDebugStubs.CANNOT_DELETE -> CwpAdStubs.CANNOT_DELETE
    AdRequestDebugStubs.BAD_SEARCH_STRING -> CwpAdStubs.BAD_SEARCH_STRING
    null -> CwpAdStubs.NONE
}

private fun CwpAdFeContext.fromApiDebug(request: IRequestAd?) {
//    this.workMode = request?.debug?.mode?.fromApiWorkMode() ?: CwpAdWorkMode.NONE
//    this.stubCase = request?.debug?.stub?.fromApiStubCase() ?: CwpAdStubs.NONE
}

private fun AdVisibility?.fromApiVisibility(): CwpAdVisibility = when (this) {
    AdVisibility.OWNER_ONLY -> CwpAdVisibility.VISIBLE_TO_OWNER
    AdVisibility.REGISTERED_ONLY -> CwpAdVisibility.VISIBLE_TO_GROUP
    AdVisibility.PUBLIC -> CwpAdVisibility.VISIBLE_PUBLIC
    null -> CwpAdVisibility.NONE
}

private fun CwpAdFeContext.resolveOperation(request: IRequestAd) {
    this.command = when (request) {
        is AdCreateRequest -> CwpAdCommand.CREATE
        is AdReadRequest -> CwpAdCommand.READ
        is AdUpdateRequest -> CwpAdCommand.UPDATE
        is AdDeleteRequest -> CwpAdCommand.DELETE
        is AdSearchRequest -> CwpAdCommand.SEARCH
        is AdOffersRequest -> CwpAdCommand.OFFERS
    }
}
