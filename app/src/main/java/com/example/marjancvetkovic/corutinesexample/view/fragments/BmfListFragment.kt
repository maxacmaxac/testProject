package com.example.marjancvetkovic.corutinesexample.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marjancvetkovic.corutinesexample.MainApplication
import com.example.marjancvetkovic.corutinesexample.OpenClassOnDebug
import com.example.marjancvetkovic.corutinesexample.R
import com.example.marjancvetkovic.corutinesexample.view.adapters.BmfAdapter
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfModelFactory
import com.example.marjancvetkovic.corutinesexample.viewModel.BmfViewModel
import com.example.marjancvetkovic.corutinesexample.viewModel.Response
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import javax.inject.Inject

@ObsoleteCoroutinesApi
@OpenClassOnDebug
class BmfListFragment : androidx.fragment.app.Fragment(), CoroutineScope {
    private val job = Job()
    override val coroutineContext = job + Dispatchers.IO

    @Inject
    lateinit var bmfModelFactory: BmfModelFactory

    lateinit var officeViewModel: BmfViewModel

    private lateinit var adapter: BmfAdapter

    protected val channel: Channel<Response> = Channel()

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
        listView.layoutManager = LinearLayoutManager(context)
        listView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        subscribeBmfs()

        floatingActionButton.setOnClickListener {
            launch(Dispatchers.IO) { officeViewModel.loadData(channel) }
        }
    }

    fun subscribeBmfs() = launch(Dispatchers.Main) {
        channel.consumeEach {
            when {
                it is Response.Error && this@BmfListFragment.isAdded -> {
                    Toast.makeText(context, "${it.error}", Toast.LENGTH_LONG).show()
                    adapter.setData(emptyList())
                }
                it is Response.Success -> {
                    adapter.setData(it.data)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        launch(Dispatchers.IO) { officeViewModel.loadData(channel) }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        fun getInstance() = BmfListFragment()
    }
}