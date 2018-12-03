package com.example.marjancvetkovic.corutinesexample.network

import com.example.marjancvetkovic.corutinesexample.model.Bmf
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit

class BmfApi(private val retrofit: Retrofit) {
    fun getBmfOffices(): Deferred<List<Bmf>> = retrofit.create(BmfInterface::class.java).getOffices()

    companion object {
        var BASE_URL = "https://service.bmf.gv.at/"
    }
}