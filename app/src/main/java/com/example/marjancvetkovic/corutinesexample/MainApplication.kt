package com.example.marjancvetkovic.corutinesexample

import android.app.Application
import com.example.marjancvetkovic.corutinesexample.dep.AppComponent
import com.example.marjancvetkovic.corutinesexample.dep.AppModule
import com.example.marjancvetkovic.corutinesexample.dep.DaggerAppComponent
import com.facebook.stetho.Stetho

open class MainApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        appComponent = initDagger(this)
    }

    private fun initDagger(app: Application): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
}