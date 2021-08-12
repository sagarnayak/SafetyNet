package com.sagar.safetynet.models

data class MobileNumber(
    var id: String = "",
    var country_code: String = "",
    var number: String = "",
    var created: String = "",
    var modified: String? = null,
)