package com.dz_dragon.simpleweather

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.beust.klaxon.Klaxon
import com.dz_dragon.simpleweather.current.Weather
import com.dz_dragon.simpleweather.forecast.Forecast
import com.dz_dragon.simpleweather.forecast.inclusion.ForecastDay
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var current_weather: Weather? = null
    private var weather_forecast: List<ForecastDay>? = null
    private var currentActivity: CurrentActivity? = null
    private var currentDay: Int = 0

    private var city_name: TextView? = null
    private var current_date: TextView? = null
    private var current_temp: TextView? = null
    private var feels_like: TextView? = null
    private var pressure: TextView? = null
    private var humidity: TextView? = null
    private var visibility: TextView? = null
    private var wind_speed: TextView? = null
    private var clouds: TextView? = null

    private var current_date_s: TextView? = null
    private var morning_temp: TextView? = null
    private var day_temp: TextView? = null
    private var evening_temp: TextView? = null
    private var night_temp: TextView? = null
    private var pressure_s: TextView? = null
    private var humidity_s: TextView? = null
    private var wind_speed_s: TextView? = null
    private var clouds_s: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentActivity = CurrentActivity.MAIN
        bindFields()
        loadWeather()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_weather -> {
                if(currentActivity == CurrentActivity.SECOND){
                    setContentView(R.layout.activity_main)
                    currentActivity = CurrentActivity.MAIN
                    bindFields()
                }
                return true
            }
            R.id.action_forecast -> {
                if(currentActivity == CurrentActivity.MAIN){
                    setContentView(R.layout.activity_second)
                    currentActivity = CurrentActivity.SECOND
                    bindFields()
                    createAdapter()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createAdapter() {
        val spinner: Spinner = findViewById(R.id.main_spinner)
        val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listOf("Сегодня", "Завтра", "Послезавтра")
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val item = parent.getItemAtPosition(position) as String
                if(item == "Сегодня") {
                    currentDay = 0
                }

                if(item == "Завтра") {
                    currentDay = 1
                }

                if(item == "Послезавтра") {
                    currentDay = 2
                }
            }
        }
    }

    private fun loadWeather() {
        Thread {
            val rawWeather = URL("https://api.openweathermap.org/" +
                    "data/2.5/weather?q=Kursk&units=metric&lang=ru&" +
                    "appid=7a3cd6c593856e4aac04d6d45e255d84"
            ).readText()

            val rawForecast = URL("https://api.openweathermap.org/data/2.5/onecall?" +
                    "lat=51.73&lon=36.19&exclude=current,minutely,hourly,alerts&units=metric&" +
                    "lang=ru&appid=7a3cd6c593856e4aac04d6d45e255d84").readText()
            runOnUiThread {
                current_weather = Klaxon().parse<Weather>(rawWeather!!)
                weather_forecast = Klaxon().parse<Forecast>(rawForecast!!)!!.forecastDay
            }
        }.start()
    }

    private fun bindFields() {
        city_name = findViewById(R.id.city_name)
        current_date = findViewById(R.id.current_date)
        current_temp = findViewById(R.id.current_temp)
        feels_like = findViewById(R.id.feels_like)
        pressure = findViewById(R.id.pressure)
        humidity = findViewById(R.id.humidity)
        visibility = findViewById(R.id.visibility)
        wind_speed = findViewById(R.id.wind_speed)
        clouds = findViewById(R.id.clouds)

        current_date_s = findViewById(R.id.current_date_s)
        morning_temp = findViewById(R.id.morning_temp)
        day_temp = findViewById(R.id.day_temp)
        evening_temp = findViewById(R.id.evening_temp)
        night_temp = findViewById(R.id.night_temp)
        pressure_s = findViewById(R.id.pressure_s)
        humidity_s = findViewById(R.id.humidity_s)
        wind_speed_s = findViewById(R.id.wind_speed_s)
        clouds_s = findViewById(R.id.clouds_s)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    fun loadButtonClick(view: View) {
        if(currentActivity == CurrentActivity.MAIN){
            city_name?.text = current_weather!!.city_name
            current_date?.text = SimpleDateFormat("dd/MM/yyyy").format(
                    Date(current_weather!!.date.toLong() * 1000)
            ).toString()
            current_temp?.text = "${current_weather!!.weatherPrm.temperature} °C"
            feels_like?.text = "${current_weather!!.weatherPrm.feels_like} °C"
            pressure?.text = current_weather!!.weatherPrm.pressure.toString()
            humidity?.text = "${current_weather!!.weatherPrm.humidity} %"
            visibility?.text = "${current_weather!!.visibility} м."
            wind_speed?.text = "${current_weather!!.breeze.speed} м/c"
            clouds?.text = "${current_weather!!.clouds.cloudiness} %"
        }

        if(currentActivity == CurrentActivity.SECOND){
            current_date_s?.text = SimpleDateFormat("dd/MM/yyyy").format(
                    Date(weather_forecast?.get(currentDay)!!.date.toLong() * 1000)
            ).toString()

            morning_temp?.text = "${weather_forecast?.get(currentDay)!!.temperature.morning} °C"
            day_temp?.text = "${weather_forecast?.get(currentDay)!!.temperature.day} °C"
            evening_temp?.text = "${weather_forecast?.get(currentDay)!!.temperature.evening} °C"
            night_temp?.text = "${weather_forecast?.get(currentDay)!!.temperature.night} °C"
            pressure_s?.text = "${weather_forecast?.get(currentDay)!!.pressure}"
            humidity_s?.text = "${weather_forecast?.get(currentDay)!!.humidity} %"
            wind_speed_s?.text = "${weather_forecast?.get(currentDay)!!.wind_speed} м/c"
            clouds_s?.text = "${weather_forecast?.get(currentDay)!!.clouds} %"
        }
    }
}