package com.dev.studyandroidbase.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Mars (
	@field:Json(name = "price")
	var price: Int,
	
	@field:Json(name = "id")
	var id: String,
	
	@field:Json(name = "type")
	var type: String,
	
	@field:Json(name = "img_src")
	var imgSrc: String
)