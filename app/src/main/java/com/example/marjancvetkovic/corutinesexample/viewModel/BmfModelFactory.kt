package com.example.marjancvetkovic.corutinesexample.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo

class BmfModelFactory(private var bmfRepo: BmfRepo) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BmfViewModel(bmfRepo) as T
    }
}