package com.tracy.testweather.logic.network

import com.tracy.testweather.logic.model.PlaceResponse
import com.tracy.testweather.SunnyWeatherApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 描述：
 * <p/>作者：LiuBao
 * <br/>创建时间：2020/8/16上午9:45.
 */
interface PlaceService {
    @GET("v2.5/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlace(@Query("query")query: String): Call<PlaceResponse>
}