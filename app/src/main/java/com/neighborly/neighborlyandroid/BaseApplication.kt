package com.neighborly.neighborlyandroid

import android.app.Application
import com.neighborly.neighborlyandroid.common.composition.AppCompositionRoot


class BaseApplication : Application() {
    lateinit var appCompositionRoot: AppCompositionRoot
    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot(this)
        super.onCreate()


    }
}