package com.example.marjancvetkovic.corutinesexample.dep

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.marjancvetkovic.corutinesexample.db.AppDatabase
import com.example.marjancvetkovic.corutinesexample.db.BmfDao
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.network.BmfApi
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    internal fun provideBmfDao(context: Context): BmfDao {
        return AppDatabase.getInstance(context).officeDao()
    }

    @Provides
    @Singleton
    internal fun provideBmfRepo(
        bmfDao: BmfDao, bmfApi: BmfApi,
        sharedPreferences: SharedPreferences
    ): BmfRepo {
        return BmfRepo(bmfDao, bmfApi, sharedPreferences)
    }

    @Provides
    @Singleton
    internal fun provideBmfModelFactory(bmfrepo: BmfRepo
    ): BmfModelFactory {
        return BmfModelFactory(bmfrepo)
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("main", Context.MODE_PRIVATE)
    }
}