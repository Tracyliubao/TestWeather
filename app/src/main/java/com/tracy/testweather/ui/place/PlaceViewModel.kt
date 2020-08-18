package com.tracy.testweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tracy.testweather.logic.Repository
import com.tracy.testweather.logic.model.Place

/**
 * 描述：
 * <p/>作者：LiuBao
 * <br/>创建时间：2020/8/16下午8:03.
 */
class PlaceViewModel : ViewModel() {
    private val searchLiveData  = MutableLiveData<String>()

    val placeList = ArrayList<Place>()//数据缓存，放在viewModel中，界面翻转时，数据不丢失

    val placeLiveData = Transformations.switchMap(searchLiveData){ Repository.searchPlaces(it) }//数据仓库中的liveData无法监听

    fun searchPlaces(query : String){
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()
}