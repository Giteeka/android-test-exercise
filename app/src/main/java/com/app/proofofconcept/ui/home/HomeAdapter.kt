package com.app.proofofconcept.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.app.proofofconcept.R
import com.app.proofofconcept.data.model.RowItem
import com.app.proofofconcept.databinding.RowItemBinding

class HomeAdapter(var list: List<RowItem>?) : RecyclerView.Adapter<HomeAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder?.rowItemBinding?.rowItem = list?.get(position)
        holder?.rowItemBinding?.executePendingBindings()
    }

    inner class DataViewHolder(var rowItemBinding: RowItemBinding) : RecyclerView.ViewHolder(rowItemBinding.root)

}