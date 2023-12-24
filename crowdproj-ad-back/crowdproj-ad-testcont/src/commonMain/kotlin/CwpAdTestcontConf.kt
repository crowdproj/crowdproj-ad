package com.crowdproj.ad.backend.testcont

import com.benasher44.uuid.uuid4

data class CwpAdTestcontConf(
    val hosts: String,
    val port: Int = 8182,
    val enableSsl: Boolean = false,
    val user: String = "root",
    val pass: String = "",
    val randomUuid: () -> String = { uuid4().toString() },
    val mustClean: Boolean = false,
)
