package com.dz_dragon.simpleweather.current

import com.beust.klaxon.Json
import com.dz_dragon.simpleweather.current.inclusion.Breeze
import com.dz_dragon.simpleweather.current.inclusion.Clouds
import com.dz_dragon.simpleweather.current.inclusion.WeatherPrm

data class Weather (
    @Json(name = "name")
        val city_name: String,

    @Json(name = "dt")
        val date: Int,

    @Json(name = "visibility")
        val visibility: Int,

    @Json(name = "main")
        val weatherPrm: WeatherPrm,

    @Json(name = "wind")
        val breeze: Breeze,

    @Json(name = "clouds")
        val clouds: Clouds
)