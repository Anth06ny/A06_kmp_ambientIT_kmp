package com.amonteiro.a06_kmp_ambientit_kmp

import android.app.Application
import com.amonteiro.a06_kmp_ambientit_kmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin { androidContext(this@MyApplication) }
    }
}