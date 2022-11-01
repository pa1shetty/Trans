package com.example.trans.utillity.di

import android.content.Context
import com.example.trans.network.MyApi
import com.example.trans.network.NetworkConnectionInterceptor
import com.example.trans.network.NetworkConstants.CONNECTION_TIME_OUT
import com.example.trans.network.NetworkConstants.READ_TIME_OUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(
        @ApplicationContext app: Context,
    ) = NetworkConnectionInterceptor(app)

    @Singleton
    @Provides
    fun provideOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor)
            .connectTimeout(CONNECTION_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
            .build()

    @Singleton
    @Provides
    fun getJson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,gson: Gson): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://w9z0g.mocklab.io/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MyApi =
        retrofit.create(MyApi::class.java)


}