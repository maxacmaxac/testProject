package com.example.marjancvetkovic.corutinesexample.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marjancvetkovic.corutinesexample.MainApplication
import com.example.marjancvetkovic.corutinesexample.R
import com.example.marjancvetkovic.corutinesexample.db.BmfRepo
import com.example.marjancvetkovic.corutinesexample.view.adapters.BmfAdapter
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfModelFactory
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject

class BmfListFragment : Fragment() {

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
        listView.layoutManager = LinearLayoutManager(context)
        listView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        officeViewModel.getBmfs().observe(this, Observer { data -> data?.let { adapter.setData(it) } })
    }

    companion object {
        fun getInstance() = BmfListFragment()
    }
}