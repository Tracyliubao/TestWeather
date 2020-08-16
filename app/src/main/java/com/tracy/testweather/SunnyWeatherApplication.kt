package com.tracy.testweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * 描述：
 * <p/>作者：LiuBao
 * <br/>创建时间：2020/8/16上午9:29.
 */
class SunnyWeatherApplication : Application() {
    companion object{
        @SuppressLint("StaticFiledLeak")//在整个程序期间不会回收，因此不会有内存泄漏的风险
        lateinit var context: Context
        const val TOKEN = "HOE3njJ2BC5BqDFY"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}