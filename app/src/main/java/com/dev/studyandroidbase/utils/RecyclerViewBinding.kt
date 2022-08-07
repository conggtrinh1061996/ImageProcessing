package com.dev.studyandroidbase.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.studyandroidbase.data.model.Filter
import com.dev.studyandroidbase.ui.adapter.ItemFilterAdapter
import com.skydoves.whatif.whatIfNotNullAs
import com.skydoves.whatif.whatIfNotNullOrEmpty

object RecyclerViewBinding {
	
	@BindingAdapter("recyclerViewFilter")
	fun recyclerViewFilter(view: RecyclerView, list: List<Filter>) {
		list.whatIfNotNullOrEmpty { item ->
			view.adapter.whatIfNotNullAs<ItemFilterAdapter> { adapter ->
				adapter.setFilters(list)
			}
		}
	}
}