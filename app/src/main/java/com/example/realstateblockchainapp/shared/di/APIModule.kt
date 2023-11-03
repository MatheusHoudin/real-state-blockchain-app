package com.example.realstateblockchainapp.shared.di

import com.example.realstateblockchainapp.shared.api.CoinApi
import com.example.realstateblockchainapp.shared.api.NFTApi
import com.example.realstateblockchainapp.shared.api.WalletApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideNftApi(get()) }
    factory { provideCoinApi(get()) }
    single { provideRetrofit(get()) }
    single { provideWalletApi(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("http://192.168.1.105:3000/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(logging)
        .connectTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .build()
}

fun provideNftApi(retrofit: Retrofit): NFTApi = retrofit.create(NFTApi::class.java)

fun provideCoinApi(retrofit: Retrofit): CoinApi = retrofit.create(CoinApi::class.java)

fun provideWalletApi(retrofit: Retrofit): WalletApi = retrofit.create(WalletApi::class.java)
