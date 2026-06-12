package com.amonteiro.a06_kmp_ambientit_kmp.data.fakedata

import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.DescriptionEntity
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.TempEntity
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.WeatherEntity
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.WindEntity
import com.amonteiro.a06_kmp_ambientit_kmp.domain.WeatherRepository

class KtorWeatherFakeRepository : WeatherRepository{

    override suspend fun loadWeathers(cityName: String): List<WeatherEntity> {
        return listOf(
            WeatherEntity(
                id = 1,
                name = "Paris",
                main = TempEntity(temp = 18.5),
                weather = listOf(
                    DescriptionEntity(description = "ciel dégagé", icon = "https://picsum.photos/200")
                ),
                wind = WindEntity(speed = 5.0)
            ),
            WeatherEntity(
                id = 2,
                name = "Toulouse",
                main = TempEntity(temp = 22.3),
                weather = listOf(
                    DescriptionEntity(description = "partiellement nuageux", icon = "https://picsum.photos/201")
                ),
                wind = WindEntity(speed = 3.2)
            ),
            WeatherEntity(
                id = 3,
                name = "Toulon",
                main = TempEntity(temp = 25.1),
                weather = listOf(
                    DescriptionEntity(description = "ensoleillé", icon = "https://picsum.photos/202")
                ),
                wind = WindEntity(speed = 6.7)
            ),
            WeatherEntity(
                id = 4,
                name = "Lyon",
                main = TempEntity(temp = 19.8),
                weather = listOf(
                    DescriptionEntity(description = "pluie légère", icon = "https://picsum.photos/203")
                ),
                wind = WindEntity(speed = 4.5)
            )
        ).shuffled()
    }
}