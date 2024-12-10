package com.mogun.shoppingapp.di

import com.mogun.data.repository.MainRepositoryImpl
import com.mogun.data.repository.TempRepositoryImpl
import com.mogun.domain.repository.MainRepository
import com.mogun.domain.repository.TempRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindTempRepository(tempRepository: TempRepositoryImpl): TempRepository

    @Binds
    @Singleton
    fun bindMainRepository(mainRepository: MainRepositoryImpl): MainRepository
}