package com.example.marjancvetkovic.corutinesexample.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.model.Bmf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class BmfViewModel() : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext = job + Dispatchers.IO
    private var officesLiveData: MutableLiveData<List<Bmf>> = MutableLiveData()

    @Inject
    constructor(bmfRepo: BmfRepo) : this() {
        launch {
            officesLiveData.postValue(bmfRepo.getOffices())
        }
    }

    fun getBmfs() = officesLiveData

    override fun onCleared() {
        job.cancel()
    }

}