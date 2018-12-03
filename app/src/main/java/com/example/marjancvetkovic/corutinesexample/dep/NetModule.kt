package com.example.marjancvetkovic.corutinesexample.dep

import com.example.marjancvetkovic.corutinesexample.network.BmfApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {
    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Authorization", "MyauthHeaderContent")
                chain.proceed(builder.build())
            }
            .build()


    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BmfApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    @Provides
    @Singleton
    internal fun provideBmfApi(retrofit: Retrofit) = BmfApi(retrofit)

    companion object {
        private const val DEFAULT_TIMEOUT: Long = 60
    }
}