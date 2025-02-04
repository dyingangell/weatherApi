package com.dyingangell.weather_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.weatherapi.com/v1/"  // Указывайте базовый URL API

    val api: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Конвертер для JSON
            .build()
            .create(WeatherApiService::class.java)
    }
}