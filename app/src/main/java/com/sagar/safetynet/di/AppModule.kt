package com.sagar.safetynet.di

import android.content.Context
import android.content.SharedPreferences
import com.sagar.android.logutilmaster.LogUtil
import com.sagar.safetynet.BuildConfig
import com.sagar.safetynet.core.BASE_URL
import com.sagar.safetynet.core.LOG_TAG
import com.sagar.safetynet.repository.ApiInterface
import com.sagar.safetynet.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesLogUtil() = LogUtil(
        LogUtil.Builder()
            .setCustomLogTag(LOG_TAG)
            .setShouldHideLogInReleaseMode(
                false,
                BuildConfig.DEBUG
            )
    )

    @Singleton
    @Provides
    fun providesSharedPref(@ApplicationContext context: Context) = SharedPrefModule(context).pref

    @Singleton
    @Provides
    fun providesNetworkModule(logUtil: LogUtil) = NetworkModule(logUtil, BASE_URL).apiInterface

    @Singleton
    @Provides
    fun providesRepository(
        pref: SharedPreferences,
        logUtil: LogUtil,
        apiInterface: ApiInterface,
    ): Repository {
        return Repository(
            pref,
            logUtil,
            apiInterface
        )
    }
}