package com.dyingangell.weather_api

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val title = findViewById<TextView>(R.id.header_title)
        val plainText = findViewById<EditText>(R.id.findCity)
        val button = findViewById<Button>(R.id.button)
        val image = findViewById<ImageView>(R.id.WeatherImage)
        val textCountry = findViewById<TextView>(R.id.weatherCountry)
        val textTemp = findViewById<TextView>(R.id.weatherTemp)
        val textOther = findViewById<TextView>(R.id.weatherOther)
        val textDiscp = findViewById<TextView>(R.id.weatherDiscp)


        button.setOnClickListener {
            start(image,plainText, title, textCountry, textTemp, textOther, textDiscp)
        }
    }


    fun start(
        image: ImageView,
        plainText: EditText,
        title: TextView,
        textCountry: TextView,
        textTemp: TextView,
        textOther: TextView,
        textDiscp: TextView
    ) {
        val apiKey = "enter ur api"  // Замените на свой API ключ
        val location = plainText.text.toString()

        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Запрос к API
                val response = RetrofitInstance.api.getCurrentWeather(apiKey, location)


                if (response.isSuccessful) {
                    val weather = response.body()
                    val discp: String
                    val imageurl = "//cdn.weatherapi.com/weather/64x64/night/116.png"
                    Glide.with(this@MainActivity)
                        .load(imageurl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE) // Отключаем кеширование
                        .skipMemoryCache(true) // Пропускаем кеширование в памяти
                        .into(image)


                    if (weather?.current?.feelslike_c != null && weather.current.feelslike_c < 10) {
                        discp = "холодно, укутайся в одежде"
                    } else if (weather?.current?.feelslike_c != null && weather.current.feelslike_c > 10 && weather.current.feelslike_c < 24) {
                        discp = "прохладно, накинься чем нибудь"
                    } else {
                        discp = "тепло, одевайся свободно"
                    }
                    Log.d("WeatherAPI", "Weather data: $weather")


                    if (weather != null) {
                        textCountry.text = "${weather.location.name}, ${weather.location.country}"
                        textTemp.text = "temperature ${weather.current.temp_c}, feels like ${weather.current.feelslike_c}"
                        textOther.text = "wind ${weather.current.wind_dir}, speed ${weather.current.wind_kph}"
                        textDiscp.text = "${weather.current.condition.text}, $discp"
                    } else {
                        title.text = "Ошибка получения данных"
                    }
                } else {
                    title.text = "Ошибка подключения"
                }
            } catch (e: Exception) {
                Log.e("Weather", "Ошибка: ${e.message}")
                title.text = "Ошибка: ${e.message}"
            }
        }
    }
}
