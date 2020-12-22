package com.dz_dragon.simpleweather.current.inclusion

import com.beust.klaxon.Json

data class WeatherPrm (
        @Json(name = "temp")
        val temperature: Double,

        @Json(name = "feels_like")
        val feels_like: Double,

        @Json(name = "pressure")
        val pressure: Int,

        @Json(name = "humidity")
        val humidity: Int
)