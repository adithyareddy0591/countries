package com.android.adithya.countries.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.countries.R
import com.android.countries.databinding.CountryItemBinding
import com.android.adithya.countries.response.CountryResponse

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    var countries = arrayListOf<CountryResponse>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CountryResponse) {
            binding.country = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<CountryItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.country_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int {
        return countries.size
    }

}