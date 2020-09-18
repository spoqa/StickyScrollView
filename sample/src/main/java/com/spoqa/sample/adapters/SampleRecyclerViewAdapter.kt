package com.spoqa.sample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spoqa.sample.R
import com.spoqa.sample.models.SampleData
import kotlinx.android.synthetic.main.item_sample.view.*
import java.text.DecimalFormat

class SampleRecyclerViewAdapter(private val dataList: ArrayList<SampleData>) :
    RecyclerView.Adapter<SampleRecyclerViewAdapter.SampleViewHolder>() {

    inner class SampleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var nameTextView: TextView = view.sample1_nameTextView
        private var standardTextView: TextView = view.sample1_standardTextView
        private var countTextView: TextView = view.sample1_countTextView
        private var unitTextView: TextView = view.sample1_unitTextView
        private var amountTextView: TextView = view.sample1_amountTextView
        private var totalAmountTextView: TextView = view.sample1_totalAmountTextView

        fun bind(item: SampleData) {
            nameTextView.text = item.name
            standardTextView.text = item.standard
            countTextView.text = item.count.toString()
            unitTextView.text = item.unit
            val df = DecimalFormat("#,###")
            amountTextView.text = df.format(item.amount)
            totalAmountTextView.text = df.format(item.totalAmount)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): SampleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sample, parent, false)
        return SampleViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size
}
