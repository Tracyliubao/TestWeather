package com.tracy.testweather

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 * 描述：
 * <p/>作者：LiuBao
 * <br/>创建时间：2020/8/16下午7:50.
 */
object Repository {
    //liveData()会自动构建并返回一个LiveData对象，然后在代码块中提供一个挂起函数的上下文，因此就可以随意的调用挂起函数了
    fun searchPlaces(query:String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e : Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)//类似于LiveData的setValue
    }
}