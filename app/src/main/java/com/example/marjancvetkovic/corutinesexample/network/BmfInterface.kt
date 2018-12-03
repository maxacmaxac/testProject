package com.example.marjancvetkovic.corutinesexample.network

import com.example.marjancvetkovic.corutinesexample.model.Bmf
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface BmfInterface {
    @GET("/Finanzamtsliste.json")
    fun getOffices(): Deferred<List<Bmf>>
}
