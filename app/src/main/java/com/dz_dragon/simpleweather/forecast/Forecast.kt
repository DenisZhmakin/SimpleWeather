package com.dz_dragon.simpleweather.forecast

import com.beust.klaxon.Json
import com.dz_dragon.simpleweather.forecast.inclusion.ForecastDay

data class Forecast (
    @Json(name = "daily")
    val forecastDay: List<ForecastDay>
)