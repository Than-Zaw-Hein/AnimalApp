package com.tzh.animal.ulti

import com.tzh.animal.data.NetworkEndpoints
import com.tzh.animal.domain.PhotoPickerRepository
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Manual dependency injection to avoid sticking to a specific dependency injection library.
 */
object Injector {

    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val ACCEPT_VERSION = "Accept-Version"

    private fun createHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest =
                chain.request().newBuilder().addHeader(CONTENT_TYPE, APPLICATION_JSON).addHeader(
                    ACCEPT_VERSION, "v1"
                ).build()
            chain.proceed(newRequest)
        }
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun createHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(createHeaderInterceptor())
        if (UnsplashPhotoPicker.isLoggingEnabled()) {
            builder.addNetworkInterceptor(createLoggingInterceptor())
        }
        val cacheSize = 10 * 1024 * 1024 // 10 MB of cache
        val cache = Cache(UnsplashPhotoPicker.getApplication().cacheDir, cacheSize.toLong())
        builder.cache(cache)
        return builder.build()
    }

    private fun createRetrofitBuilder(): Retrofit {
        return Retrofit.Builder().baseUrl(NetworkEndpoints.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(
                createHttpClient()
            ).build()
    }

    private fun createNetworkEndpoints(): NetworkEndpoints =
        createRetrofitBuilder().create(NetworkEndpoints::class.java)


    fun createRepository(): PhotoPickerRepository {
        return PhotoPickerRepository(createNetworkEndpoints())
    }

//    fun createPickerViewModelFactory(): UnsplashPickerViewModelFactory {
//        return UnsplashPickerViewModelFactory(createRepository())
//    }
}
