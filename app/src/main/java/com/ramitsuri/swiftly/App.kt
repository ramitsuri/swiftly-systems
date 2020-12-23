package com.ramitsuri.swiftly

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.ramitsuri.swiftly.dependency.Injector
import timber.log.Timber

class App : Application(), LifecycleObserver {
    lateinit var injector: Injector

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLogging()
        initInjector()
    }

    private fun initInjector() {
        injector = Injector()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var instance: App
            private set
    }
}