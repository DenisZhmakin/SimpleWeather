package com.dz_dragon.simpleweather.forecast.inclusion

import com.beust.klaxon.Json

data class Temperature (
    @Json(name = "day")
    val day: Double,

    @Json(name = "night")
    val night: Double,

    @Json(name = "eve")
    val evening: Double,

    @Json(name = "morn")
    val morning: Double
)