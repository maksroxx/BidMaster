package com.roxx.bidmaster.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.roxx.bidmaster.data.network.api.BidApi
import com.roxx.bidmaster.data.preferences.PreferencesImpl
import com.roxx.bidmaster.data.repository.BidRepositoryImpl
import com.roxx.bidmaster.domain.preferences.Preferences
import com.roxx.bidmaster.domain.repository.BidRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBidRepository(api: BidApi): BidRepository {
        return BidRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_pref", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(
        sharedPreferences: SharedPreferences
    ): Preferences {
        return PreferencesImpl(sharedPreferences)
    }
}