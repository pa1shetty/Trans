package com.example.trans.utillity.di

import android.content.Context
import com.example.trans.utillity.firebase.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {
    @Provides
    @FragmentScoped
    fun provideFirebaseAuth(
        @ActivityContext app: Context,
    ) = FirebaseAuth(app)
}