package com.dev.studyandroidbase.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.data.model.AdjustModel
import com.dev.studyandroidbase.databinding.ItemAdjustImageBinding
import com.dev.studyandroidbase.ui.adapter.AdjustImageAdapter.AdjustImageViewHolder
import java.text.NumberFormat

class AdjustImageAdapter(context: Context): RecyclerView.Adapter<AdjustImageViewHolder>() {
	
	var onSliderChange: ((position: Int, value: Float) -> Unit)? = null
	
	val listData = listOf(
		AdjustModel( "Brightness"),
		AdjustModel("Contrast")
	)
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdjustImageViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding: ItemAdjustImageBinding = DataBindingUtil.inflate(inflater, R.layout.item_adjust_image, parent, false)
		return AdjustImageViewHolder(binding)
	}
	
	override fun onBindViewHolder(holder: AdjustImageViewHolder, position: Int) {
		holder.onBind(listData[position], position)
	}
	
	override fun getItemCount(): Int = listData.size
	
	inner class AdjustImageViewHolder(binding: ItemAdjustImageBinding): RecyclerView.ViewHolder(binding.root) {
		private val itemBinding = binding
		
		fun onBind(adjustImage: AdjustModel, position: Int) {
			itemBinding.textContent.text = adjustImage.title
			itemBinding.slider.setLabelFormatter { value ->
				val numberFormat = NumberFormat.getNumberInstance()
				numberFormat.format(value.toInt())
			}
			itemBinding.slider.addOnChangeListener {_, value,_ ->
				adjustImage.value = value
				onSliderChange?.invoke(position, value)
			}
		}
	}
	
}