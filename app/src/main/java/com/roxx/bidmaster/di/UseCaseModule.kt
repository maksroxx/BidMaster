package com.roxx.bidmaster.di

import com.roxx.bidmaster.domain.preferences.Preferences
import com.roxx.bidmaster.domain.repository.BidRepository
import com.roxx.bidmaster.domain.use_case.CreateUserUseCase
import com.roxx.bidmaster.domain.use_case.GetTopUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        bidRepository: BidRepository,
        preferences: Preferences
    ): CreateUserUseCase {
        return CreateUserUseCase(bidRepository, preferences)
    }

    @Provides
    @Singleton
    fun provideTopUsersUseCse(
        bidRepository: BidRepository
    ): GetTopUsersUseCase {
        return GetTopUsersUseCase(bidRepository)
    }
}