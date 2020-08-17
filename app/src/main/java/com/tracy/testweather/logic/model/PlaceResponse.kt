package com.tracy.testweather.logic.model

import com.google.gson.annotations.SerializedName


/**
 * 描述：
 * <p/>作者：LiuBao
 * <br/>创建时间：2020/8/16上午9:42.
 */
data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)