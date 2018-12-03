package com.example.marjancvetkovic.corutinesexample.view.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.marjancvetkovic.corutinesexample.MainApplication
import com.example.marjancvetkovic.corutinesexample.R
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.view.adapters.BmfAdapter
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfModelFactory
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject

class BmfListFragment : androidx.fragment.app.Fragment() {

    @Inject
    lateinit var bmfModelFactory: BmfModelFactory

    private lateinit var officeViewModel: BmfViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        officeViewModel = ViewModelProviders.of(this, bmfModelFactory).get(BmfViewModel::class.java)
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BmfAdapter()
        listView.adapter = adapter
        listView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        listView.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                context,
                androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
            )
        )
        officeViewModel.getBmfs().observe(this, Observer { data -> data?.let { adapter.setData(it) } })
        officeViewModel.getDataState()
            .observe(this,
                Observer { state -> if (!state) Toast.makeText(context, "Error", Toast.LENGTH_LONG).show() })
    }

    companion object {
        fun getInstance() = BmfListFragment()
    }
}