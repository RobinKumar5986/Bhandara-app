package com.kgJr.bhandara.data.remote.apiUtils

import android.content.Context
import androidx.room.Room
import com.kgJr.bhandara.data.local.dao.LocationDao
import com.kgJr.bhandara.data.local.roomDB.AppDatabase
import com.kgJr.bhandara.data.remote.Api
import com.kgJr.bhandara.data.remote.repository.AppRepository
import com.kgJr.bhandara.data.remote.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): Api {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(1,TimeUnit.MINUTES)
            .readTimeout(1,TimeUnit.MINUTES)
            .writeTimeout(1,TimeUnit.MINUTES)
            .build()

        return Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.APPLICATION_DB
        ).build()
    }


    @Provides
    @Singleton
    fun provideUserRepository(api: Api): AppRepository {
        return AppRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLocationDao(appDatabase: AppDatabase): LocationDao {
        return appDatabase.locationDao()
    }
}
