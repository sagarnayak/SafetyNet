package com.sagar.safetynet.models

data class AuthenticationRequest(
    val userEmail: String = "",
    val password: String = "",
)