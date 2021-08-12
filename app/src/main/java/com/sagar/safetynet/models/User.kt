package com.sagar.safetynet.models

data class User(
    var id: String = "",
    var role: String = "",
    var password: String = "",
    var first_name: String = "",
    var last_name: String? = null,
    var email_id: String = "",
    var mobile_number: MobileNumber? = null,
    var enabled: Boolean = false,
    var created: String = "",
    var modified: String? = null,
)