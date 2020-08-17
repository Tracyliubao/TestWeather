package com.tracy.testweather.logic.network

import com.tracy.testweather.logic.interceptor.LoggerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 描述：
 * <p/>作者：LiuBao
 * <br/>创建时间：2020/8/16上午10:01.
 */
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"
    var timeout = 5 //请求超时时间

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getBuilder().build())
        .build()

    private fun getBuilder() : OkHttpClient.Builder{
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .addInterceptor(LoggerInterceptor())
        return builder
    }

    fun <T> create(serviceClass : Class<T>) : T = retrofit.create(serviceClass)

    inline fun <reified T> create() : T =
        create(T::class.java)
}