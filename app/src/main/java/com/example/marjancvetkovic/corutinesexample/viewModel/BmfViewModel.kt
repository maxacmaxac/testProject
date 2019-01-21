package com.example.marjancvetkovic.corutinesexample.viewModel

import androidx.lifecycle.ViewModel
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.model.Bmf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class BmfViewModel(private val bmfRepo: BmfRepo) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext = job + Dispatchers.IO
    private val channel = Channel<Response>()

    fun loadData() {
        launch {
            channel.send(try {
                Response(bmfRepo.getOffices())
            } catch (e: Exception) {
                Response(error = e)
            })
        }
    }

    fun getBmfs() = channel

    override fun onCleared() {
        job.cancel()
    }

}

data class Response(val data: List<Bmf> = listOf(), val error: Throwable? = null)