package com.tracy.testweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * 描述：
 * <p/>作者：LiuBao
 * <br/>创建时间：2020/8/16上午10:08.
 */
object SunnyWeatherNetwork {
    private val placeService =
        ServiceCreator.create(PlaceService::class.java)

    suspend fun searchPlaces(query : String) = placeService.searchPlace(query).await()

    private suspend fun <T> Call<T>.await() : T{
        return suspendCoroutine {continuation ->
            enqueue(object : Callback<T>{
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null){
                        continuation.resume(body)
                    }else{
                        continuation.resumeWithException(RuntimeException("body is null"))
                    }
                }
            })
        }
    }
}