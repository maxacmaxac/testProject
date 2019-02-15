package com.example.marjancvetkovic.corutinesexample.mock

import com.example.marjancvetkovic.corutinesexample.OpenClassOnDebug
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfViewModel
import com.example.marjancvetkovic.corutinesexample.viewModel.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.mockito.Mockito

@OpenClassOnDebug
class MockBmfViewModel(val response: Response) : BmfViewModel(Mockito.mock(BmfRepo::class.java)), CoroutineScope {

    private val job = Job()
    override val coroutineContext = job + Dispatchers.IO
    val channel = Channel<Response>()

    override fun loadData() {
        launch {
            channel.send(response)
        }
    }

    override fun getBmfs() = channel

    override fun onCleared() {
        job.cancel()
    }

}