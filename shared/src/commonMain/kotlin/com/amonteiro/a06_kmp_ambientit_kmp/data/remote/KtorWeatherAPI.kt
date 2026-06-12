package com.amonteiro.a06_kmp_ambientit_kmp.data.remote

import com.amonteiro.a06_kmp_ambientit_kmp.BuildConfig
import com.amonteiro.a06_kmp_ambientit_kmp.di.initKoin
import com.amonteiro.a06_kmp_ambientit_kmp.domain.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.mp.KoinPlatform

suspend fun main() {

    initKoin()
    val ktorWeatherApi : KtorWeatherApi =  KoinPlatform.getKoin().get<KtorWeatherApi>()

    for (weather in ktorWeatherApi.loadWeathers("nice")) {
        println(weather.getResume())
    }

}

class KtorWeatherApi(val client : HttpClient) : WeatherRepository {

   override suspend fun loadWeathers(cityName: String): List<WeatherEntity> {

        val response = client.get("https://api.openweathermap.org/data/2.5/find?q=$cityName&appid=${BuildConfig.WEATHER_API_KEY}&units=metric&lang=fr")
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        val list = response.body<WeatherAPIResult>().list
        list.forEach{
            it.weather.forEach {
                it.icon = "https://openweathermap.org/img/wn/${it.icon}@4x.png"
            }
        }

        return list
    }

    fun close() {
        client.close()
    }
}

/* -------------------------------- */
// WEATHER
/* -------------------------------- */
@Serializable
data class WeatherAPIResult(val list: List<WeatherEntity>)

@Serializable
data class WeatherEntity(
    val id: Int, val name: String, var main: TempEntity,
    var weather: List<DescriptionEntity>,
    var wind: WindEntity
) {

    fun getResume() = """
            Il fait ${main.temp}° à $name (id=$id) avec un vent de ${wind.speed} m/s
            -Description : ${weather.firstOrNull()?.description ?: "-"}
            -Icône : ${weather.firstOrNull()?.icon ?: "-"}
        """.trimIndent()

}

@Serializable
data class TempEntity(var temp: Double)

@Serializable
data class DescriptionEntity(var description: String, var icon: String)

@Serializable
data class WindEntity(var speed: Double)
