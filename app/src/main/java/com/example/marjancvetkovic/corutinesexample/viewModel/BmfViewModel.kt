package com.example.marjancvetkovic.corutinesexample.viewModel

import android.util.Log
import android.widget.Toast
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
    private var officesLiveData: MutableLiveData<List<Bmf>> = MutableLiveData()
    private val dataState: MutableLiveData<Boolean> = MutableLiveData()

    @Inject
    constructor(bmfRepo: BmfRepo) : this() {
        launch {
            try {
                officesLiveData.postValue(bmfRepo.getOffices())
                dataState.postValue(true)
            } catch (e: Exception) {
                dataState.postValue(false)
            }
        }
    }

    fun getDataState() = dataState

    fun getBmfs() = officesLiveData

    override fun onCleared() {
        job.cancel()
    }

}