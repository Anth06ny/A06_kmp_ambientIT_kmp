package com.amonteiro.a06_kmp_ambientit_kmp

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}