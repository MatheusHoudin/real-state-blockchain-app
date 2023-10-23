package com.example.realstateblockchainapp.shared.di

import com.example.realstateblockchainapp.shared.api.CoinApi
import com.example.realstateblockchainapp.shared.api.NFTApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideNftApi(get()) }
    factory { provideCoinApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("http://192.168.1.101:3000/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    return OkHttpClient().newBuilder().addInterceptor(logging).build()
}

fun provideNftApi(retrofit: Retrofit): NFTApi = retrofit.create(NFTApi::class.java)

fun provideCoinApi(retrofit: Retrofit): CoinApi = retrofit.create(CoinApi::class.java)
