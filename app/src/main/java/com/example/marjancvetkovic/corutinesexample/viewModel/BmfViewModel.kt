package com.example.marjancvetkovic.corutinesexample.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.model.Bmf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class BmfViewModel() : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext = job + Dispatchers.IO
    private var officesLiveData: MutableLiveData<Response> = MutableLiveData()

    @Inject
    constructor(bmfRepo: BmfRepo) : this() {
        launch {
            try {
                officesLiveData.postValue(Response(bmfRepo.getOffices()))
            } catch (e: Exception) {
                officesLiveData.postValue(Response(error = e))
            }
        }
    }


    fun getBmfs() = officesLiveData

    override fun onCleared() {
        job.cancel()
    }

}

data class Response(val data: List<Bmf> = listOf(), val error: Throwable? = null)