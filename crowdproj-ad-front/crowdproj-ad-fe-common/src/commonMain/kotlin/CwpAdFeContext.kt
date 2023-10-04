package com.crowdproj.ad.common

import com.crowdproj.ad.common.models.CwpAdCommand
import com.crowdproj.ad.common.models.CwpAdRequestId

data class CwpAdFeContext(
    var requestId: CwpAdRequestId = CwpAdRequestId.NONE,
    var command: CwpAdCommand = CwpAdCommand.NONE,
)
