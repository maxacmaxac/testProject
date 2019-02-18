package com.example.marjancvetkovic.corutinesexample.mock

import com.example.marjancvetkovic.corutinesexample.OpenClassOnDebug
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfViewModel
import com.example.marjancvetkovic.corutinesexample.viewModel.Response
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import org.mockito.Mockito

@OpenClassOnDebug
class MockBmfViewModel(val response: Response) : BmfViewModel(Mockito.mock(BmfRepo::class.java)) {
    override suspend fun loadData(channel: Channel<Response>) {
        channel.send(response)
    }
}