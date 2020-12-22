package com.dz_dragon.simpleweather.current.inclusion

import com.beust.klaxon.Json

data class Breeze (
        @Json(name = "speed")
        val speed: Double,

        @Json(name = "deg")
        val deg: Int
)