package com.amonteiro.a06_kmp_ambientit_kmp.domain

import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.WeatherEntity

interface WeatherRepository {
    suspend fun loadWeathers(cityName: String): List<WeatherEntity>
}