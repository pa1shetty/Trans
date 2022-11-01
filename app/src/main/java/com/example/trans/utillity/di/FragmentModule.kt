package com.example.trans.utillity.di

import android.content.Context
import com.example.trans.data.datastore.AppDataStore
import com.example.trans.utillity.AesLibrary
import com.example.trans.utillity.UtilClass
import com.example.trans.utillity.firebase.FirebaseAuth
import com.example.trans.utillity.firebase.FirebaseConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {
    @Provides
    @FragmentScoped
    fun provideFirebaseAuth(
        @ActivityContext app: Context,
    ) = FirebaseAuth(app)
}