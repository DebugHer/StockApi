package com.plcoding.stockmarketapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(@Query("apiKey") apiKey: String): ResponseBody

    companion object{
        const val API_KET = "Q63Y9NX3TUF587NF"
        const val BASE_API = "https://alphavantage.co"
    }
}