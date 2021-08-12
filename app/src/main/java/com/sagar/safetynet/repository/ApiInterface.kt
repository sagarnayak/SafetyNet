package com.sagar.safetynet.repository

import com.sagar.safetynet.models.AuthenticationRequest
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("authenticate")
    fun authenticateUser(
        @Body authenticationRequest: AuthenticationRequest
    ): Observable<Response<ResponseBody>>
}