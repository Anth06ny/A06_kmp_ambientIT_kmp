package com.amonteiro.a06_kmp_ambientit_kmp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.amonteiro.a06_kmp_ambientit_kmp.data.remote.KtorWeatherApi
import com.amonteiro.a06_kmp_ambientit_kmp.di.apiFakeModule
import com.amonteiro.a06_kmp_ambientit_kmp.di.apiModule
import com.amonteiro.a06_kmp_ambientit_kmp.di.viewModelModule
import com.amonteiro.a06_kmp_ambientit_kmp.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatform
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class MainViewModelTest {

    @Test
    fun loadWeatherMockTest() = runTest(timeout = 10.seconds) {

        startKoin {
            modules(apiFakeModule, viewModelModule)
        }

        val mainViewModel = KoinPlatform.getKoin().get<MainViewModel>()

        assertFalse { mainViewModel.runInProgress.value  }
        mainViewModel.loadWeathers("Toulouse")
        assertTrue { mainViewModel.runInProgress.value  }

        mainViewModel.runInProgress.first { !it }

        assertFalse { mainViewModel.runInProgress.value  }
        assertTrue(mainViewModel.dataList.value.isNotEmpty())
    }

    @Test
    fun loadWeatherTest() = runTest(timeout = 10.seconds) {

        startKoin {
            modules(apiModule, viewModelModule)
        }

        val mainViewModel = KoinPlatform.getKoin().get<MainViewModel>()

        assertFalse { mainViewModel.runInProgress.value  }
        mainViewModel.loadWeathers("Toulouse")
        assertTrue { mainViewModel.runInProgress.value  }

        mainViewModel.runInProgress.first { !it }

        assertFalse { mainViewModel.runInProgress.value  }
        assertTrue(mainViewModel.dataList.value.isNotEmpty())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }
}