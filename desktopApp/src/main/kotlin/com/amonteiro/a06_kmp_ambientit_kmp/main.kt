package com.amonteiro.a06_kmp_ambientit_kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.amonteiro.a06_kmp_ambientit_kmp.di.initKoin

fun main() = application {

    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "A06_kmp_ambientIT_kmp",
    ) {
        App()
    }
}