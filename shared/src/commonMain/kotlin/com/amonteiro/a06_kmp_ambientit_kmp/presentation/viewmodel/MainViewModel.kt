package com.amonteiro.a06_kmp_ambientit_kmp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.DescriptionEntity
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.KtorWeatherApi
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.TempEntity
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.WeatherEntity
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.WindEntity
import com.amonteiro.a06_kmp_ambientit_kmp.domain.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch



class MainViewModel(val ktorWeatherApi: WeatherRepository) : ViewModel() {
    val dataList = MutableStateFlow(emptyList<WeatherEntity>())
    val runInProgress = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")

    init {//Création d'un jeu de donnée au démarrage
        println("Instanciation de MainViewModel")
        //loadFakeData()
    }

    fun loadWeathers(cityName: String) {
        runInProgress.value = true
        errorMessage.value = ""
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (cityName.length < 3) {
                    throw Exception("Il faut au moins 3 caractères")
                }
                dataList.value = ktorWeatherApi.loadWeathers(cityName)
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = e.message ?: "Une erreur est survenue"
            }
            runInProgress.value = false
        }
    }

    //Version avancée
    fun loadFakeData(runInProgress: Boolean = false, errorMessage: String = "") {
        this.runInProgress.value = runInProgress
        this.errorMessage.value = errorMessage
        dataList.value = listOf(
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
        ).shuffled() //shuffled() pour avoir un ordre différent à chaque appel
    }

    //on ne peut pas l'utiliser dans la Preview
    //fun loadWeathers(cityName: String){ /* vrai requête */ }
}