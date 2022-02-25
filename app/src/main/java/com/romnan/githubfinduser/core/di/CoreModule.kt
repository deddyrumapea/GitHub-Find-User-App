package com.romnan.githubfinduser.core.di

import android.app.Application
import androidx.room.Room
import com.romnan.githubfinduser.core.data.local.CoreDatabase
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
    fun provideCoreRetrofit(): Retrofit {
        // TODO: use BuildConfig to get base url and API key
        val headerInterceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "token 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")
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCoreDatabase(app: Application): CoreDatabase {
        return Room.databaseBuilder(
            app,
            CoreDatabase::class.java,
            CoreDatabase.NAME
        ).build()
    }
}