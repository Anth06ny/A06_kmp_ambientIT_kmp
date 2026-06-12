package com.amonteiro.a06_kmp_ambientit_kmp.di

import com.amonteiro.a06_kmp_ambientit_kmp.data.fakedata.KtorWeatherFakeRepository
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.KtorWeatherApi
import com.amonteiro.a06_kmp_ambientit_kmp.domain.WeatherRepository
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.viewmodel.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

//Si besoin du contexte, pour le passer en paramètre au lancement de Koin
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(apiModule, viewModelModule)
    }
}

// Version pour iOS et Desktop
fun initKoin() = initKoin {}

//------------------------
//DECLARATION DES MODULES
//------------------------
val apiModule = module {
    //Création d'un singleton pour le client HTTP
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }
        }
    }

    singleOf(::KtorWeatherApi) bind WeatherRepository::class
}

val apiFakeModule = module {
    singleOf(::KtorWeatherFakeRepository) bind WeatherRepository::class
}

//Version spécifique au ViewModel
val viewModelModule = module {

    viewModelOf(::MainViewModel)
}