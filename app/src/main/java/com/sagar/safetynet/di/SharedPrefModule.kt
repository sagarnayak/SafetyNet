package com.sagar.safetynet.di

import android.content.Context
import android.content.SharedPreferences
import com.sagar.safetynet.core.SHARED_PREF_DB

class SharedPrefModule(context: Context) {

    var pref: SharedPreferences = context.getSharedPreferences(
        SHARED_PREF_DB,
        Context.MODE_PRIVATE
    )
}