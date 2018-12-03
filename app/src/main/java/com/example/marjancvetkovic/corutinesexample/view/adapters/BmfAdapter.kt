package com.example.marjancvetkovic.corutinesexample.view.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.marjancvetkovic.corutinesexample.R
import com.example.marjancvetkovic.corutinesexample.model.Bmf
import kotlinx.android.synthetic.main.bmf_row.view.*

class BmfAdapter : RecyclerView.Adapter<SimpleViewHolder>() {

    var dataList: List<Bmf> = arrayListOf()

    fun setData(data: List<Bmf>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return SimpleViewHolder(BmfItem(parent.context))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val bmf = dataList[position]
        val item = holder.itemView as BmfItem
        item.setTitle(bmf.name ?: "")
        item.setSubTitle(bmf.zip.toString())
    }
}

class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(checkNotNull(view))

class BmfItem(context: Context) : ConstraintLayout(context) {
    init {
        inflate(context, R.layout.bmf_row, this)
    }

    fun setTitle(title: String) {
        textViewOfficeTitle.text = title
    }

    fun setSubTitle(title: String) {
        textViewOfficeSubTitle.text = title
    }

}