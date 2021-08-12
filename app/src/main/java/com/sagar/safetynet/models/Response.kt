package com.sagar.safetynet.models

data class Response(
    val nonce: String? = null,
    val timestampMs: Long? = null,
    val apkPackageName: String? = null,
    val apkCertificateDigestSha256: ArrayList<String>? = null,
    val apkDigestSha256: String? = null,
    val ctsProfileMatch: Boolean? = null,
    val basicIntegrity: Boolean? = null
)