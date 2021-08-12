@file:Suppress("unused")

package com.sagar.safetynet.utils

import androidx.lifecycle.ViewModel
import com.sagar.safetynet.repository.Repository

open class SuperViewModel(private val repository: Repository) :
    ViewModel() {

    private var activityPaused = false
    private var shouldNotifyWhileActivityPaused = true

    val mutableLiveDataProcessing: SuperMutableLiveData<Boolean> = SuperMutableLiveData()
    val mutableLiveDataGenericError: SuperMutableLiveData<Result> = SuperMutableLiveData()
    val mutableLiveDataCriticalError: SuperMutableLiveData<Result> = SuperMutableLiveData()

    fun loading() = mutableLiveDataProcessing.getSuccess().postValue(Event(true))

    fun doneLoading() = mutableLiveDataProcessing.getSuccess().postValue(Event(false))

    fun genericError(message: String? = null, result: Result? = null) {
        message?.let {
            mutableLiveDataGenericError.getSuccess().postValue(
                Event(
                    Result(
                        StatusCode.Unknown.code,
                        message = message,
                        result = ResultType.FAIL
                    )
                )
            )
        }
        result?.let {
            mutableLiveDataGenericError.getSuccess().postValue(
                Event(
                    result
                )
            )
        }
    }

    fun criticalError(message: String? = null, result: Result? = null) {
        message?.let {
            mutableLiveDataCriticalError.getSuccess().postValue(
                Event(
                    Result(
                        StatusCode.Unknown.code,
                        message = message,
                        result = ResultType.FAIL
                    )
                )
            )
        }
        result?.let {
            mutableLiveDataCriticalError.getSuccess().postValue(
                Event(
                    result
                )
            )
        }
    }

    fun notifyDuringPaused(shouldNotifyWhileActivityPaused: Boolean) {
        this.shouldNotifyWhileActivityPaused = shouldNotifyWhileActivityPaused
    }

    fun activityPaused() {
        activityPaused = true
    }

    fun activityResumed() {
        activityPaused = false
    }

    fun canPushData(): Boolean {
        if (shouldNotifyWhileActivityPaused)
            return true
        return !activityPaused
    }

    fun giveRepository(): Repository {
        return repository
    }
}