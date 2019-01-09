package com.example.marjancvetkovic.corutinesexample.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.marjancvetkovic.corutinesexample.MainApplication
import com.example.marjancvetkovic.corutinesexample.R
import com.example.marjancvetkovic.corutinesexample.view.adapters.BmfAdapter
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfModelFactory
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfViewModel
import com.example.marjancvetkovic.corutinesexample.viewModel.Response
import kotlinx.android.synthetic.main.list_fragment.*
import javax.inject.Inject

class BmfListFragment : androidx.fragment.app.Fragment() {

    @Inject
    lateinit var bmfModelFactory: BmfModelFactory

    private lateinit var officeViewModel: BmfViewModel

    private lateinit var adapter: BmfAdapter

    private val dataObserver: Observer<Response> = Observer { data ->
        data?.let {
            if (it.error != null) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            } else {
                adapter.setData(it.data)
            }
        }
    }

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
        adapter = BmfAdapter()
        listView.adapter = adapter
        listView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        listView.addItemDecoration(
                androidx.recyclerview.widget.DividerItemDecoration(
                        context,
                        androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
                )
        )
    }

    override fun onResume() {
        super.onResume()
        officeViewModel.getBmfs().observe(this, dataObserver)
    }

    override fun onPause() {
        super.onPause()
        officeViewModel.getBmfs().removeObserver(dataObserver)
    }

    companion object {
        fun getInstance() = BmfListFragment()
    }
}