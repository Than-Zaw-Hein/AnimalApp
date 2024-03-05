package com.tzh.animal.di

import com.tzh.animal.domain.PhotoPickerRepository
import com.tzh.animal.ulti.Injector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providePhotoPickerModule(): PhotoPickerRepository = Injector.createRepository()
}