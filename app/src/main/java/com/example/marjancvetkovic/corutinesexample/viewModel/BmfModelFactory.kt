package com.example.marjancvetkovic.corutinesexample.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo

class BmfModelFactory(private var bmfRepo: BmfRepo) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BmfViewModel(bmfRepo) as T
    }
}