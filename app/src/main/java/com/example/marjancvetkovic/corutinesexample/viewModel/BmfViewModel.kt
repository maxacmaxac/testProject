package com.example.marjancvetkovic.corutinesexample.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.marjancvetkovic.corutinesexample.OpenClassOnDebug
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.model.Bmf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@OpenClassOnDebug
class BmfViewModel(val bmfRepo: BmfRepo) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext = job + Dispatchers.IO
    private val channel = Channel<Response>()

    fun loadData() {
        launch(Dispatchers.IO) {
            channel.send(
                try {
                    val elements = bmfRepo.getOffices()
                    Response.Success(elements)
                } catch (e: Exception) {
                    Log.e("", e.toString())
                    Response.Error(error = e)
                }
            )
        }
    }

    fun getBmfs() = channel

    override fun onCleared() {
        job.cancel()
    }

}

sealed class Response {
    class Success(val data: List<Bmf> = listOf()) : Response()
    class Error(val error: Throwable? = null) : Response()
}