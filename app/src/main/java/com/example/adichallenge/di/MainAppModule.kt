package com.example.adichallenge.di

import com.example.adichallenge.network.AdidasProductService
import com.example.adichallenge.repo.ProductRepository
import com.example.adichallenge.repo.ProductRepositoryImpl
import com.example.adichallenge.utils.HttpClient
import com.example.adichallenge.utils.LoggingInterceptor
import com.example.adichallenge.utils.MoshiCreator
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainAppModule {

    @Provides
    fun provideLoggingInterceptor() = LoggingInterceptor.create()

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        HttpClient.create(httpLoggingInterceptor)

    @Singleton
    @Provides
    fun provideProductsApi(retrofit: Retrofit): AdidasProductService =
        retrofit.create(AdidasProductService::class.java)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = MoshiCreator.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi, baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    fun provideOrdersBaseUrl(): String = "https://609c06b02b549f00176e4f21.mockapi.io/"

    @Provides
    fun providesProductRepository(
        service: AdidasProductService
    ): ProductRepository = ProductRepositoryImpl(service)
}