package com.tracy.testweather.logic

import androidx.lifecycle.liveData
import com.tracy.testweather.logic.model.Weather
import com.tracy.testweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * 描述：
 * <p/>作者：LiuBao
 * <br/>创建时间：2020/8/16下午7:50.
 */
object Repository {
    //liveData()会自动构建并返回一个LiveData对象，然后在代码块中提供一个挂起函数的上下文，因此就可以随意的调用挂起函数了
    fun searchPlaces(query:String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok"){
            val places = placeResponse.places
            Result.success(places)
        }else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String,lat: String) = fire(Dispatchers.IO){
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng,lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                Result.success(weather)
            }else{
                Result.failure(RuntimeException("realtime status is ${realtimeResponse.status} + daily status is ${dailyResponse.status}"))
            }
        }
    }

    private fun <T> fire(context:CoroutineContext,block : suspend() -> Result<T>) = liveData<Result<T>>(context) {
        val result = try {
            block()
        }catch (e : Exception){
            Result.failure<T>(e)
        }
        emit(result)//类似于LiveData的setValue
    }
}