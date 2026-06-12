package com.amonteiro.a06_kmp_ambientit_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform