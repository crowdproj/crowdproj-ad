package com.crowdproj.ad.common.exceptions

import com.crowdproj.ad.common.models.CwpAdLock

class RepoConcurrencyException(expectedLock: CwpAdLock, actualLock: CwpAdLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
