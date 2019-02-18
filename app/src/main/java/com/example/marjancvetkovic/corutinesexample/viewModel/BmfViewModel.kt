package com.example.marjancvetkovic.corutinesexample.viewModel

import androidx.lifecycle.ViewModel
import com.example.marjancvetkovic.corutinesexample.OpenClassOnDebug
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.model.Bmf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withTimeout

@OpenClassOnDebug
class BmfViewModel(val bmfRepo: BmfRepo) : ViewModel() {
    suspend fun loadData(channel: Channel<Response>) {
        channel.send(
            try {
                val elements = withTimeout(5000) { bmfRepo.getOffices() }
                Response.Success(elements)
            } catch (e: Exception) {
                Response.Error(error = e)
            }
        )
    }
}

sealed class Response {
    class Success(val data: List<Bmf> = listOf()) : Response()
    class Error(val error: Throwable? = null) : Response()
}