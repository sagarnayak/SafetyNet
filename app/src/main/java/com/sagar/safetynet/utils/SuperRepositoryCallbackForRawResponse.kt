package com.sagar.safetynet.utils

import okhttp3.ResponseBody

interface SuperRepositoryCallbackForRawResponse {
    fun giveRawResponse(response: ResponseBody?) {}
}