package com.dz_dragon.simpleweather.current.inclusion

import com.beust.klaxon.Json

data class Clouds (
    @Json(name = "all")
    val cloudiness: Int
)