package com.roxx.bidmaster.di

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
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
    fun provideEncryptedSharedPreferences(
        app: Application
    ): SharedPreferences {
        val masterKeyAlias = MasterKey.Builder(app)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            app,
            "encrypted_shared_pref",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun providePreferences(
        sharedPreferences: SharedPreferences
    ): Preferences {
        return PreferencesImpl(sharedPreferences)
    }
}