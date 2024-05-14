package com.sdevprem.roadcastassignment.di

import com.sdevprem.roadcastassignment.data.datasource.MovieDataSource
import com.sdevprem.roadcastassignment.data.datasource.constants.ApiConfig
import com.sdevprem.roadcastassignment.data.datasource.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .build();


    @Singleton
    @Provides
    fun providesRetrofit(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)

        return retrofit.build()
    }

    @Singleton
    @Provides
    fun providesMoviesDataSource(retrofit: Retrofit): MovieDataSource =
        retrofit.create(MovieDataSource::class.java)

}