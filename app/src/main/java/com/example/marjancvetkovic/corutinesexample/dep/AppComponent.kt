package com.example.marjancvetkovic.corutinesexample.dep

import android.content.SharedPreferences
import com.example.marjancvetkovic.corutinesexample.MainActivity
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.view.fragments.BmfListFragment
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: BmfListFragment)

    fun retrofit(): Retrofit
    fun okHttpClient(): OkHttpClient

    fun repo(): BmfRepo
    fun sharedPreferences(): SharedPreferences
}