package com.sagar.safetynet.ui

import com.sagar.safetynet.models.AuthenticationRequest
import com.sagar.safetynet.models.AuthenticationResponse
import com.sagar.safetynet.repository.Repository
import com.sagar.safetynet.utils.SuperMediatorLiveData
import com.sagar.safetynet.utils.SuperViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repository: Repository) :
    SuperViewModel(repository) {

    val mediatorLiveDataAuthenticationResponse: SuperMediatorLiveData<AuthenticationResponse> =
        SuperMediatorLiveData()

    init {
        mediatorLiveDataAuthenticationResponse.initialise(
            repository.mutableLiveDataAuthentication
        )
    }

    fun authenticate(authenticationRequest: AuthenticationRequest) =
        repository.authenticateUser(authenticationRequest)
}