package com.dev.studyandroidbase.enum

enum class Answer {
	YES,
	NO,

	MAYBE {
		override fun toString(): String = "-=+<>"
	}
}