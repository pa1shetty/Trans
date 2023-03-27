package com.example.trans.utillity.di

import com.example.trans.utillity.firebase.FireBaseBb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideFirebaseDb() = FireBaseBb()
}