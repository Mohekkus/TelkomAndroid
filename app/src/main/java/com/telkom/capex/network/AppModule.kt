package com.telkom.capex.network

import com.telkom.capex.network.interceptor.RetrofitInterceptor
import com.telkom.capex.network.services.BudgetService
import com.telkom.capex.network.services.DashboardService
import com.telkom.capex.network.services.LoginService
import com.telkom.capex.network.services.TrackerService
import com.telkom.capex.ui.login.data.helper.TokenHelper
import com.telkom.capex.ui.login.data.implementor.TokenImplementor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun setupLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    fun provideBaseURL() = ModuleInfo.getAddress()

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, retrofitInterceptor: RetrofitInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(retrofitInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .callTimeout(2, TimeUnit.MINUTES)
            .build()

    @Singleton
    @Provides
    fun setupRetrofit(okHttpClient: OkHttpClient, baseURL: String): Retrofit =
         Retrofit.Builder()
             .addConverterFactory(JacksonConverterFactory.create())
             .baseUrl(baseURL)
             .client(okHttpClient)
             .build()

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService = retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideDashboardService(retrofit: Retrofit): DashboardService = retrofit.create(DashboardService::class.java)

    @Singleton
    @Provides
    fun provideBudgetService(retrofit: Retrofit): BudgetService = retrofit.create(BudgetService::class.java)

    @Singleton
    @Provides
    fun provideTrackerService(retrofit: Retrofit): TrackerService = retrofit.create(TrackerService::class.java)

    @Singleton
    @Provides
    fun provideTokenHelper(tokenImplementor: TokenImplementor): TokenHelper = tokenImplementor

}