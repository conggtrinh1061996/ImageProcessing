package com.dev.studyandroidbase.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.data.model.Filter
import com.dev.studyandroidbase.databinding.ItemFilterBinding
import com.dev.studyandroidbase.ui.adapter.ItemFilterAdapter.FilterViewHolder

class ItemFilterAdapter(context: Context): RecyclerView.Adapter<FilterViewHolder>() {
	
	var itemClick: ((position: Int) -> Unit)? = null
	
	private val listColor = listOf(
		ContextCompat.getColor(context, R.color.color_nofilter),
		ContextCompat.getColor(context, R.color.color_red_0_3a),
		ContextCompat.getColor(context, R.color.color_green_0_3a),
		ContextCompat.getColor(context, R.color.color_blue_0_3a),
		ContextCompat.getColor(context, R.color.gray),
		ContextCompat.getColor(context, R.color.color_nofilter)
	)
	//
	var listFilter = mutableListOf(
		Filter("No Filter", listColor[0]),
		Filter("Red", listColor[1]),
		Filter("Green", listColor[2]),
		Filter("Blue", listColor[3]),
		Filter("Gray", listColor[4]),
		Filter("Reverse", listColor[5])
	)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = DataBindingUtil.inflate<ItemFilterBinding>(inflater, R.layout.item_filter, parent, false)
		return FilterViewHolder(binding)
	}
	
	override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
		holder.onBind(listFilter[position], position)
	}
	
	override fun getItemCount(): Int = listFilter.size
	
	inner class FilterViewHolder(binding: ItemFilterBinding): RecyclerView.ViewHolder(binding.root) {
		val itemBinding: ItemFilterBinding = binding
		
		fun onBind(filter: Filter, position: Int) {
			itemBinding.textFilter.text = filter.contextDecription
			itemBinding.demoFilter.setBackgroundColor(filter.imageFilter)
			itemBinding.item.setOnClickListener {
				itemClick?.invoke(position)
			}
		}
		
	}
	
	fun setFilters(list: List<Filter>) {
		if (list.isEmpty()) emptyList<Filter>()
		listFilter.apply {
			clear()
			addAll(list)
			notifyDataSetChanged()
		}
		
	}
	
}