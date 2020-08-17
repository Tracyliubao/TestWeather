package com.tracy.testweather.logic.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject

class LoggerInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        printAddress("请求地址-->", request)
        printRequest("发送请求-->", request)
        val response = chain.proceed(request)
        val responseBody = response.peekBody(1024 * 1024)
        printResponse("接受响应-->", responseBody.string())
        return response
    }

    /**
     * 打印请求地址
     *
     * @param tag
     * @param request
     */
    private fun printAddress(tag : String , request : Request){
        Log.e(tag,String.format("%s",request.url()))
    }
    /**
     * 打印请求入参
     *
     * @param tag
     * @param request
     */
    private fun printRequest(tag : String , request : Request){
        var message = ""
        val requestBody = request.body()
        requestBody?.let {
            val buffer = okio.Buffer()
            try {
                requestBody.writeTo(buffer)
                var charset = Charsets.UTF_8
                val contentType = requestBody.contentType()
                contentType?.let {
                    charset = it.charset(charset)!!
                }
                message = buffer.readString(charset)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
        Log.e(tag,message)
    }
    /**
     * 打印请求出参
     *
     * @param tag
     * @param msg
     */
    private fun printResponse(tag : String , msg :String){
        var message = ""
        message = try {
            when {
                msg.startsWith("{") -> {
                    val jsonObject = JSONObject(msg)
                    jsonObject.toString()
                }
                msg.startsWith("[") -> {
                    val jsonArray = JSONArray(msg)
                    jsonArray.toString()
                }
                else -> {
                    msg
                }
            }
        }catch (e : Exception){
            msg
        }
        Log.e(tag,message)
    }
}