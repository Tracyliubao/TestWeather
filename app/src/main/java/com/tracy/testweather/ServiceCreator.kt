package com.tracy.testweather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 描述：
 * <p/>作者：LiuBao
 * <br/>创建时间：2020/8/16上午10:01.
 */
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass : Class<T>) : T = retrofit.create(serviceClass)

    inline fun <reified T> create() : T = create(T::class.java)
}