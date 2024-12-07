package com.roxx.bidmaster.di

import com.roxx.bidmaster.domain.preferences.Preferences
import com.roxx.bidmaster.domain.repository.BidRepository
import com.roxx.bidmaster.domain.use_case.BidStateUseCase
import com.roxx.bidmaster.domain.use_case.CreateUserUseCase
import com.roxx.bidmaster.domain.use_case.DeleteBidUseCase
import com.roxx.bidmaster.domain.use_case.GetBidsUseCase
import com.roxx.bidmaster.domain.use_case.GetLastBidUseCase
import com.roxx.bidmaster.domain.use_case.GetMyInformationUseCase
import com.roxx.bidmaster.domain.use_case.GetTopUsersUseCase
import com.roxx.bidmaster.domain.use_case.LoginUserUseCase
import com.roxx.bidmaster.domain.use_case.MakeBidUseCase
import com.roxx.bidmaster.domain.use_case.ValidateTokenUseCase
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
    fun provideLoginUserUseCase(
        bidRepository: BidRepository,
        preferences: Preferences
    ): LoginUserUseCase {
        return LoginUserUseCase(bidRepository, preferences)
    }

    @Provides
    @Singleton
    fun provideTopUsersUseCse(
        bidRepository: BidRepository
    ): GetTopUsersUseCase {
        return GetTopUsersUseCase(bidRepository)
    }

    @Provides
    @Singleton
    fun provideUserInformation(bidRepository: BidRepository): GetMyInformationUseCase {
        return GetMyInformationUseCase(bidRepository)
    }

    @Provides
    @Singleton
    fun provideMakeBidUseCase(
        bidRepository: BidRepository,
        preferences: Preferences
    ): MakeBidUseCase {
        return MakeBidUseCase(bidRepository, preferences)
    }

    @Provides
    @Singleton
    fun provideBidsUseCase(bidRepository: BidRepository): GetBidsUseCase {
        return GetBidsUseCase(bidRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteBidUseCase(
        bidRepository: BidRepository,
        preferences: Preferences
    ): DeleteBidUseCase {
        return DeleteBidUseCase(bidRepository, preferences)
    }

    @Provides
    @Singleton
    fun provideBidStateUseCase(preferences: Preferences): BidStateUseCase {
        return BidStateUseCase(preferences)
    }

    @Provides
    @Singleton
    fun provideLastBidUseCase(
        bidRepository: BidRepository,
        preferences: Preferences
    ): GetLastBidUseCase {
        return GetLastBidUseCase(bidRepository, preferences)
    }

    @Provides
    @Singleton
    fun provideValidateToken(
        bidRepository: BidRepository,
        preferences: Preferences
    ): ValidateTokenUseCase {
        return ValidateTokenUseCase(bidRepository, preferences)
    }
}