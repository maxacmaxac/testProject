package com.example.marjancvetkovic.corutinesexample.db

import android.content.SharedPreferences
import com.example.marjancvetkovic.corutinesexample.model.Bmf
import com.example.marjancvetkovic.corutinesexample.network.BmfApi
import java.util.concurrent.TimeUnit

class BmfRepo(
    private val bmfDao: BmfDao, private val bmfApi: BmfApi,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun getOffices(): List<Bmf> {
        if (sharedPreferences.getLong(BMF_LIST_KEY, 0) < System.currentTimeMillis()) {
            val offices = bmfApi.getBmfOffices()
            bmfDao.deleteAll()
            bmfDao.insertAll(offices.await())
            sharedPreferences.edit().putLong(BMF_LIST_KEY, System.currentTimeMillis() + TimeUnit.HOURS.toMillis(4))
                .apply()
        }
        return bmfDao.allOffices
    }

    companion object {
        const val BMF_LIST_KEY = "BMF_LIST_KEY"
    }

}