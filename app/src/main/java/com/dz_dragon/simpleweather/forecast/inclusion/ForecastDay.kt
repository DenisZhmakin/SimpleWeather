package com.dz_dragon.simpleweather.forecast.inclusion

import com.beust.klaxon.Json

data class ForecastDay (
    @Json(name = "dt")
    val date: Int,

    @Json(name = "temp")
    val temperature: Temperature,

    @Json(name = "pressure")
    val pressure: Int,

    @Json(name = "humidity")
    val humidity: Int,

    @Json(name = "wind_speed")
    val wind_speed: Double,

    @Json(name = "clouds")
    val clouds: Int
)