package com.sagar.safetynet.utils

interface SuperRepositoryCallback<in T> {
    fun success(result: T) {}
    fun notAuthorised() {}
    fun noContent() {}
    fun error(result: Result) {}
    fun moreAuthRequired() {}
}