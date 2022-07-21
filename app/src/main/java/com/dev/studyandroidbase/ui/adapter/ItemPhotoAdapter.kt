package com.dev.studyandroidbase.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.studyandroidbase.R
import com.dev.studyandroidbase.data.model.Mars
import com.dev.studyandroidbase.databinding.ItemPhotoBinding
import com.dev.studyandroidbase.ui.adapter.ItemPhotoAdapter.ItemViewHolder
import com.dev.studyandroidbase.utils.GliderHelper

class ItemPhotoAdapter(): RecyclerView.Adapter<ItemViewHolder>() {
	
	var listPhoto = mutableListOf<Mars>()
	var onClickItem: ((View, Int, Mars) -> Unit)? = null
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding: ItemPhotoBinding = DataBindingUtil.inflate(inflater, R.layout.item_photo, parent, false)
		return ItemViewHolder(binding)
	}
	
	override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
		holder.onBind(listPhoto[position])
		holder.itemView.setOnClickListener {
			onClickItem?.invoke(it, position, listPhoto[position])
		}
	}
	
	override fun getItemCount(): Int = listPhoto.size
	
	class ItemViewHolder(var binding: ItemPhotoBinding): RecyclerView.ViewHolder(binding.root) {
		
		fun onBind(mars: Mars) {
			binding.textName.text = mars.id
			GliderHelper.loadImage(binding.image, mars.imgSrc)
		}
	}
	
	fun setData(list: List<Mars>) {
		listPhoto.clear()
		listPhoto.addAll(list)
		notifyDataSetChanged()
	}
	
}