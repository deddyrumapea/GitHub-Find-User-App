package com.romnan.githubfinduser.core.di

import com.romnan.githubfinduser.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideGitHubRetrofit(): Retrofit {
        val headerInterceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", BuildConfig.GITHUB_API_KEY)
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}