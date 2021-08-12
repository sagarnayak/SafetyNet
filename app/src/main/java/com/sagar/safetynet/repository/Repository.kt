package com.sagar.safetynet.repository

import android.content.SharedPreferences
import com.sagar.android.logutilmaster.LogUtil
import com.sagar.safetynet.models.AuthenticationRequest
import com.sagar.safetynet.models.AuthenticationResponse
import com.sagar.safetynet.utils.Result
import com.sagar.safetynet.utils.SuperMutableLiveData
import com.sagar.safetynet.utils.SuperRepository
import com.sagar.safetynet.utils.SuperRepositoryCallback

class Repository(
    private val pref: SharedPreferences,
    private val logUtil: LogUtil,
    private val apiInterface: ApiInterface,
) : SuperRepository() {

    val mutableLiveDataAuthentication: SuperMutableLiveData<AuthenticationResponse> =
        SuperMutableLiveData()

    fun authenticateUser(authenticationRequest: AuthenticationRequest) {
        makeApiCall(
            apiInterface.authenticateUser(
                authenticationRequest
            ),
            callback = object : SuperRepositoryCallback<AuthenticationResponse> {
                override fun success(result: AuthenticationResponse) {
                    super.success(result)

                    logUtil.logV("got the response")
                }

                override fun error(result: Result) {
                    super.error(result)

                    logUtil.logV("got the error")
                }
            }
        )
    }
}