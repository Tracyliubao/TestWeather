package com.tracy.testweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tracy.testweather.logic.Repository
import com.tracy.testweather.logic.model.Location

class WeatherModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()

    var lng = ""

    var lat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData){
        Repository.refreshWeather(it.lng,it.lat)
    }

    fun refreshWeather(lng : String ,lat : String){
        locationLiveData.value = Location(lng,lat)
    }
}